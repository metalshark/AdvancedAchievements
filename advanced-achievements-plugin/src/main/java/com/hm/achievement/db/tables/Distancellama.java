/*
 * This file is generated by jOOQ.
 */
package com.hm.achievement.db.tables;

import com.hm.achievement.db.DefaultSchema;
import com.hm.achievement.db.Keys;
import com.hm.achievement.db.tables.records.DistancellamaRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Distancellama extends TableImpl<DistancellamaRecord> {

	private static final long serialVersionUID = 1131380869;

	/**
	 * The reference instance of <code>distancellama</code>
	 */
	public static final Distancellama DISTANCELLAMA = new Distancellama();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<DistancellamaRecord> getRecordType() {
		return DistancellamaRecord.class;
	}

	/**
	 * The column <code>distancellama.playername</code>.
	 */
	public final TableField<DistancellamaRecord, String> PLAYERNAME = createField(DSL.name("playername"),
			org.jooq.impl.SQLDataType.CHAR(36), this, "");

	/**
	 * The column <code>distancellama.distancellama</code>.
	 */
	public final TableField<DistancellamaRecord, Long> DISTANCELLAMA_ = createField(DSL.name("distancellama"),
			org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>distancellama</code> table reference
	 */
	public Distancellama() {
		this(DSL.name("distancellama"), null);
	}

	/**
	 * Create an aliased <code>distancellama</code> table reference
	 */
	public Distancellama(String alias) {
		this(DSL.name(alias), DISTANCELLAMA);
	}

	/**
	 * Create an aliased <code>distancellama</code> table reference
	 */
	public Distancellama(Name alias) {
		this(alias, DISTANCELLAMA);
	}

	private Distancellama(Name alias, Table<DistancellamaRecord> aliased) {
		this(alias, aliased, null);
	}

	private Distancellama(Name alias, Table<DistancellamaRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
	}

	public <O extends Record> Distancellama(Table<O> child, ForeignKey<O, DistancellamaRecord> key) {
		super(child, key, DISTANCELLAMA);
	}

	@Override
	public Schema getSchema() {
		return DefaultSchema.DEFAULT_SCHEMA;
	}

	@Override
	public UniqueKey<DistancellamaRecord> getPrimaryKey() {
		return Keys.PK_DISTANCELLAMA;
	}

	@Override
	public List<UniqueKey<DistancellamaRecord>> getKeys() {
		return Arrays.<UniqueKey<DistancellamaRecord>> asList(Keys.PK_DISTANCELLAMA);
	}

	@Override
	public Distancellama as(String alias) {
		return new Distancellama(DSL.name(alias), this);
	}

	@Override
	public Distancellama as(Name alias) {
		return new Distancellama(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Distancellama rename(String name) {
		return new Distancellama(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Distancellama rename(Name name) {
		return new Distancellama(name, null);
	}

	// -------------------------------------------------------------------------
	// Row2 type methods
	// -------------------------------------------------------------------------

	@Override
	public Row2<String, Long> fieldsRow() {
		return (Row2) super.fieldsRow();
	}
}
