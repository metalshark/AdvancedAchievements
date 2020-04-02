package com.hm.achievement.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;

import com.hm.achievement.category.MultipleAchievements;
import com.hm.achievement.category.NormalAchievements;
import com.hm.achievement.db.data.AwardedDBAchievement;
import com.hm.achievement.exception.PluginLoadError;
import com.hm.achievement.lifecycle.Reloadable;
import com.hm.mcshared.file.CommentedYamlConfiguration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.MappedTable;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

import static com.hm.achievement.db.Tables.ACHIEVEMENTS;
import static com.hm.achievement.db.Tables.CONNECTIONS;
import static org.jooq.impl.DSL.count;

/**
 * Abstract class in charge of factoring out common functionality for the database manager.
 *
 * @author Pyves
 */
public abstract class AbstractDatabaseManager implements Reloadable {

	// Used to do perform the database write operations asynchronously.
	ExecutorService pool;

	// Database Connection Pool
	private HikariDataSource dataSource;
	private DSLContext create;
	private SQLDialect sqlDialect;

	final CommentedYamlConfiguration mainConfig;
	final Logger logger;
	final String dataSourceClassName;

	volatile String prefix;

	private final Map<String, String> namesToDisplayNames;
	private final DatabaseUpdater databaseUpdater;

	private DateFormat dateFormat;
	private boolean configBookChronologicalOrder;

	public AbstractDatabaseManager(CommentedYamlConfiguration mainConfig, Logger logger,
			Map<String, String> namesToDisplayNames, DatabaseUpdater databaseUpdater, String dataSourceClassName,
			SQLDialect sqlDialect) {
		this.mainConfig = mainConfig;
		this.logger = logger;
		this.namesToDisplayNames = namesToDisplayNames;
		this.databaseUpdater = databaseUpdater;
		this.dataSourceClassName = dataSourceClassName;
		this.sqlDialect = sqlDialect;
		// We expect to execute many short writes to the database. The pool can grow dynamically under high load and
		// allows to reuse threads.
		pool = Executors.newCachedThreadPool();
	}

	@Override
	public void extractConfigurationParameters() {
		configBookChronologicalOrder = mainConfig.getBoolean("BookChronologicalOrder", true);
		String localeString = mainConfig.getString("DateLocale", "en");
		boolean dateDisplayTime = mainConfig.getBoolean("DateDisplayTime");
		Locale locale = new Locale(localeString);
		if (dateDisplayTime) {
			dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);
		} else {
			dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		}
	}

	abstract HikariConfig getConfig();

	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Initialises the database system by extracting settings, performing setup tasks and updating schemas if necessary.
	 *
	 * @throws PluginLoadError
	 */
	public void initialise() throws PluginLoadError {
		logger.info("Initialising database...");

		prefix = mainConfig.getString("TablePrefix", "");

		try {
			performPreliminaryTasks();
		} catch (ClassNotFoundException e) {
			logger.severe("The JBDC driver for the chosen database type was not found.");
		}

		// Try to establish connection pool with database
		final HikariConfig config = getConfig();
		dataSource = new HikariDataSource(config);
		Settings settings = new Settings()
				.withRenderMapping(new RenderMapping()
						.withSchemata(
								new MappedSchema().withInputExpression(Pattern.compile("(.*)"))
										.withTables(
												new MappedTable().withInputExpression(Pattern.compile("(.*)"))
														.withOutput(prefix + "$1"))));
		create = DSL.using(dataSource, sqlDialect, settings);

		databaseUpdater.renameExistingTables(this);
		databaseUpdater.initialiseTables(this);
		databaseUpdater.updateOldDBToMaterial(this);
		databaseUpdater.updateOldDBToDates(this);
		databaseUpdater.updateOldDBToTimestamps(this);
		Arrays.stream(MultipleAchievements.values()).forEach(m -> databaseUpdater.updateOldDBColumnSize(this, m));
	}

	/**
	 * Performs any needed tasks before opening a connection to the database.
	 *
	 * @throws ClassNotFoundException
	 * @throws PluginLoadError
	 */
	abstract void performPreliminaryTasks() throws ClassNotFoundException, PluginLoadError;

	/**
	 * Shuts the thread pool down and closes connection to database.
	 */
	public void shutdown() {
		pool.shutdown();
		try {
			// Wait a few seconds for remaining tasks to execute.
			if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
				logger.warning("Some write operations could not be sent to the database during plugin shutdown.");
			}
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "Error while waiting for database write operations to complete:", e);
			Thread.currentThread().interrupt();
		} finally {
			if (!dataSource.isClosed())
				dataSource.close();
		}
	}

	/**
	 * Gets the list of names of all the achievements of a player.
	 *
	 * @param uuid
	 * @return array list with Name parameters
	 */
	public List<String> getPlayerAchievementNamesList(UUID uuid) {
		final String playername = uuid.toString();
		return ((SQLReadOperation<List<String>>) () -> create
				.select(ACHIEVEMENTS.ACHIEVEMENT)
				.from(ACHIEVEMENTS)
				.where(ACHIEVEMENTS.PLAYERNAME.equal(playername))
				.fetch(record -> record.get(ACHIEVEMENTS.ACHIEVEMENT)))
						.executeOperation("retrieving the names of received achievements");
	}

	/**
	 * Gets the reception date of a specific achievement.
	 *
	 * @param uuid
	 * @param achName
	 * @return date represented as a string
	 */
	public String getPlayerAchievementDate(UUID uuid, String achName) {
		return ((SQLReadOperation<String>) () -> create
				.select(ACHIEVEMENTS.DATE)
				.from(ACHIEVEMENTS)
				.where(ACHIEVEMENTS.PLAYERUUID.equal(uuid))
				.and(achName.contains("'") ? ACHIEVEMENTS.ACHIEVEMENT.equal(achName)
						: ACHIEVEMENTS.ACHIEVEMENT.equal(achName)
								.or(ACHIEVEMENTS.ACHIEVEMENT.equal(StringUtils.replace(achName, "'", "''"))))
				.fetchOne(record -> dateFormat.format(record.get(ACHIEVEMENTS.DATE))))
						.executeOperation("retrieving an achievement's reception date");
	}

	/**
	 * Gets the total number of achievements received by every player; this method is provided as a convenience for
	 * other plugins.
	 *
	 * @return map containing number of achievements for every players
	 */
	public Map<UUID, Integer> getPlayersAchievementsAmount() {
		return ((SQLReadOperation<Map<UUID, Integer>>) () -> create
				.select(ACHIEVEMENTS.PLAYERUUID, count())
				.from(ACHIEVEMENTS)
				.groupBy(ACHIEVEMENTS.PLAYERUUID)
				.fetchMap(ACHIEVEMENTS.PLAYERUUID, count()))
						.executeOperation("counting all players' achievements");
	}

	/**
	 * Gets the total number of achievements received by a player, using an UUID.
	 *
	 * @param uuid
	 * @return number of achievements
	 */
	public int getPlayerAchievementsAmount(UUID uuid) {
		return ((SQLReadOperation<Integer>) () -> create
				.selectCount()
				.from(ACHIEVEMENTS)
				.where(ACHIEVEMENTS.PLAYERUUID.equal(uuid))
				.fetchOneInto(Integer.class))
						.executeOperation("counting a player's achievements");
	}

	/**
	 * Constructs a mapping of players with the most achievements over a given period.
	 *
	 * @param start
	 * @return LinkedHashMap with keys corresponding to player UUIDs and values corresponding to their achievement count
	 */
	public Map<String, Integer> getTopList(long start) {
		final LocalDateTime date = new Timestamp(start).toLocalDateTime();
		return ((SQLReadOperation<Map<String, Integer>>) () -> create
				.select(ACHIEVEMENTS.PLAYERNAME, count())
				.from(ACHIEVEMENTS)
				.where((start == 0L) ? null : ACHIEVEMENTS.DATE.greaterThan(date))
				.groupBy(ACHIEVEMENTS.PLAYERNAME)
				.orderBy(count().desc())
				.fetchMap(ACHIEVEMENTS.PLAYERNAME, count()))
						.executeOperation("computing the list of top players");
	}

	/**
	 * Registers a new achievement for a player with the reception time set to now.
	 *
	 * @param uuid
	 * @param achName
	 * @param achMessage
	 */
	public void registerAchievement(UUID uuid, String achName, String achMessage) {
		registerAchievement(uuid, achName, achMessage, System.currentTimeMillis());
	}

	/**
	 * Registers a new achievement for a player.
	 *
	 * @param uuid
	 * @param achName
	 * @param achMessage
	 * @param epochMs Moment the achievement was registered at.
	 */
	void registerAchievement(UUID uuid, String achName, String achMessage, long epochMs) {
		final LocalDateTime date = new Timestamp(epochMs).toLocalDateTime();
		((SQLWriteOperation) () -> create
				.insertInto(ACHIEVEMENTS, ACHIEVEMENTS.PLAYERNAME, ACHIEVEMENTS.ACHIEVEMENT, ACHIEVEMENTS.DESCRIPTION,
						ACHIEVEMENTS.DATE)
				.values(uuid.toString(), achName, achMessage, date)
				.onDuplicateKeyUpdate()
				.set(ACHIEVEMENTS.DESCRIPTION, achMessage)
				.set(ACHIEVEMENTS.DATE, date)
				.execute())
						.executeOperation(pool, logger, "registering an achievement");
	}

	/**
	 * Checks whether player has received a specific achievement.
	 *
	 * @param uuid
	 * @param achName
	 * @return true if achievement found in database, false otherwise
	 */
	public boolean hasPlayerAchievement(UUID uuid, String achName) {
		final String playername = uuid.toString();
		return ((SQLReadOperation<Boolean>) () -> create
				.select(ACHIEVEMENTS.ACHIEVEMENT)
				.from(ACHIEVEMENTS)
				.where(ACHIEVEMENTS.PLAYERNAME.eq(playername))
				.and((achName.contains("'"))
						? ACHIEVEMENTS.ACHIEVEMENT.eq(achName)
								.or(ACHIEVEMENTS.ACHIEVEMENT.eq(StringUtils.replace(achName, "'", "''")))
						: ACHIEVEMENTS.ACHIEVEMENT.eq(achName))
				.fetch()
				.isNotEmpty())
						.executeOperation("checking for an achievement");
	}

	/**
	 * Gets a player's NormalAchievement statistic.
	 *
	 * @param uuid
	 * @param category
	 * @return statistic
	 */
	public long getNormalAchievementAmount(UUID uuid, NormalAchievements category) {
		String dbName = category.toDBName();
		String sql = "SELECT " + dbName + " FROM " + prefix + dbName + " WHERE playername = ?";
		return ((SQLReadOperation<Long>) () -> {
			try (final Connection conn = dataSource.getConnection();
					final PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setObject(1, uuid, Types.CHAR);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getLong(dbName);
				}
			}
			return 0L;
		}).executeOperation("retrieving " + category + " statistics");
	}

	/**
	 * Gets a player's MultipleAchievement statistic.
	 *
	 * @param uuid
	 * @param category
	 * @param subcategory
	 * @return statistic
	 */
	public long getMultipleAchievementAmount(UUID uuid, MultipleAchievements category, String subcategory) {
		String dbName = category.toDBName();
		String sql = "SELECT " + dbName + " FROM " + prefix + dbName + " WHERE playername = ? AND "
				+ category.toSubcategoryDBName() + " = ?";
		return ((SQLReadOperation<Long>) () -> {
			try (final Connection conn = dataSource.getConnection();
					final PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setObject(1, uuid, Types.CHAR);
				ps.setString(2, subcategory);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getLong(dbName);
				}
			}
			return 0L;
		}).executeOperation("retrieving " + category + "." + subcategory + " statistics");
	}

	/**
	 * Returns a player's number of connections on separate days (used by GUI).
	 *
	 * @param uuid
	 * @return connections statistic
	 */
	public int getConnectionsAmount(UUID uuid) {
		String dbName = NormalAchievements.CONNECTIONS.toDBName();
		String sql = "SELECT " + dbName + " FROM " + prefix + dbName + " WHERE playername = ?";
		return ((SQLReadOperation<Integer>) () -> {
			try (final Connection conn = dataSource.getConnection();
					final PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setObject(1, uuid, Types.CHAR);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getInt(dbName);
				}
			}
			return 0;
		}).executeOperation("retrieving connection statistics");
	}

	/**
	 * Gets a player's last connection date.
	 *
	 * @param uuid
	 * @return String with date
	 */
	public String getPlayerConnectionDate(UUID uuid) {
		String dbName = NormalAchievements.CONNECTIONS.toDBName();
		String sql = "SELECT date FROM " + prefix + dbName + " WHERE playername = ?";
		return ((SQLReadOperation<String>) () -> {
			try (final Connection conn = dataSource.getConnection();
					final PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setObject(1, uuid, Types.CHAR);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getString("date");
				}
			}
			return null;
		}).executeOperation("retrieving a player's last connection date");
	}

	/**
	 * Updates a player's number of connections and last connection date and returns number of connections (used by
	 * Connections listener).
	 *
	 * @param uuid
	 * @param date
	 * @return connections statistic
	 */
	public int updateAndGetConnection(UUID uuid, String date) {
		final String dbName = NormalAchievements.CONNECTIONS.toDBName();
		final String sqlRead = "SELECT " + dbName + " FROM " + prefix + dbName + " WHERE playername = ?";
		final int connections = ((SQLReadOperation<Integer>) () -> {
			try (final Connection conn = dataSource.getConnection();
					final PreparedStatement ps = conn.prepareStatement(sqlRead)) {
				ps.setObject(1, uuid, Types.CHAR);
				ResultSet rs = ps.executeQuery();
				return rs.next() ? rs.getInt(dbName) + 1 : 1;
			}
		}).executeOperation("handling connection event");

		String sqlWrite = "REPLACE INTO " + prefix + dbName + " VALUES (?,?,?)";
		((SQLWriteOperation) () -> {
			try (final Connection conn = dataSource.getConnection();
					final PreparedStatement ps = conn.prepareStatement(sqlWrite)) {
				ps.setObject(1, uuid, Types.CHAR);
				ps.setInt(2, connections);
				ps.setString(3, date);
				ps.execute();
			}
		}).executeOperation(pool, logger, "updating connection date and count");

		return connections;
	}

	/**
	 * Deletes an achievement from a player.
	 *
	 * @param uuid
	 * @param achName
	 */
	public void deletePlayerAchievement(UUID uuid, String achName) {
		((SQLWriteOperation) () -> create
			.delete(ACHIEVEMENTS)
			.where(ACHIEVEMENTS.PLAYERNAME.eq(uuid.toString()))
			.and((achName.contains("'")) ?
				ACHIEVEMENTS.ACHIEVEMENT.eq(achName).or(ACHIEVEMENTS.ACHIEVEMENT.eq(StringUtils.replace(achName, "'", "''"))) :
				ACHIEVEMENTS.ACHIEVEMENT.eq(achName))
			.execute())
					.executeOperation(pool, logger, "deleting an achievement");
	}

	/**
	 * Clears Connection statistics for a given player.
	 *
	 * @param uuid
	 */
	public void clearConnection(UUID uuid) {
		((SQLWriteOperation) () -> create
			.delete(CONNECTIONS)
			.where(CONNECTIONS.PLAYERNAME.eq(uuid.toString()))
			.execute())
					.executeOperation(pool, logger, "clearing connection statistics");
	}

	String getPrefix() {
		return prefix;
	}

	/**
	 * Returns a list of AwardedDBAchievements get by a player.
	 *
	 * @param uuid UUID of a player.
	 * @return ArrayList containing all information about achievements awarded to a player.
	 */
	public List<AwardedDBAchievement> getPlayerAchievementsList(UUID uuid) {
		return ((SQLReadOperation<List<AwardedDBAchievement>>) () -> create
			.select(ACHIEVEMENTS.PLAYERNAME, ACHIEVEMENTS.ACHIEVEMENT, ACHIEVEMENTS.DESCRIPTION, ACHIEVEMENTS.DATE)
			.from(ACHIEVEMENTS)
			.where(ACHIEVEMENTS.PLAYERNAME.eq(uuid.toString()))
			.orderBy((configBookChronologicalOrder) ? ACHIEVEMENTS.DATE : ACHIEVEMENTS.DATE.desc())
			.fetch((record) -> {
				String achName = record.get(ACHIEVEMENTS.ACHIEVEMENT);
				achName = StringUtils.replace(achName, "''", "'");
				final String displayName = namesToDisplayNames.get(achName);
				if (StringUtils.isNotBlank(displayName)) {
					achName = displayName;
				}
				final Timestamp dateAwarded = Timestamp.valueOf(record.get(ACHIEVEMENTS.DATE));
				return new AwardedDBAchievement(uuid, achName, record.get(ACHIEVEMENTS.DESCRIPTION), dateAwarded.getTime(), dateFormat.format(dateAwarded));
			}))
			.executeOperation("retrieving the full data of received achievements");
	}

	/**
	 * Retrieve matching list of achievements for a name of an achievement.
	 * <p>
	 * Limited to 1000 most recent entries to save memory.
	 *
	 * @param achievementName Name of an achievement in database format.
	 * @return List of AwardedDBAchievement objects, message field is empty to save memory.
	 */
	public List<AwardedDBAchievement> getAchievementsRecipientList(String achievementName) {
		return ((SQLReadOperation<List<AwardedDBAchievement>>) () -> create
				.select(ACHIEVEMENTS.PLAYERNAME, ACHIEVEMENTS.DATE)
				.from(ACHIEVEMENTS)
				.where(ACHIEVEMENTS.ACHIEVEMENT.eq(achievementName))
				.orderBy(ACHIEVEMENTS.DATE.desc())
				.limit(1000)
				.fetch((record) -> {
					final Date dateAwarded = new Date(Timestamp.valueOf(record.get(ACHIEVEMENTS.DATE)).getTime());
					return new AwardedDBAchievement(record.get(ACHIEVEMENTS.PLAYERUUID),
						namesToDisplayNames.get(achievementName), "", dateAwarded.getTime(),
						dateFormat.format(dateAwarded));
				}))
								.executeOperation("retrieving the recipients of an achievement");
	}
}
