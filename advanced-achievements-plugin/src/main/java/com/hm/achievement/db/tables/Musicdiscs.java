/*
 * This file is generated by jOOQ.
 */
package com.hm.achievement.db.tables;

import com.hm.achievement.db.DefaultSchema;
import com.hm.achievement.db.Keys;
import com.hm.achievement.db.tables.records.MusicdiscsRecord;

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
public class Musicdiscs extends TableImpl<MusicdiscsRecord> {

	private static final long serialVersionUID = -582886392;

	/**
	 * The reference instance of <code>musicdiscs</code>
	 */
	public static final Musicdiscs MUSICDISCS = new Musicdiscs();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<MusicdiscsRecord> getRecordType() {
		return MusicdiscsRecord.class;
	}

	/**
	 * The column <code>musicdiscs.playername</code>.
	 */
	public final TableField<MusicdiscsRecord, String> PLAYERNAME = createField(DSL.name("playername"),
			org.jooq.impl.SQLDataType.CHAR(36), this, "");

	/**
	 * The column <code>musicdiscs.musicdiscs</code>.
	 */
	public final TableField<MusicdiscsRecord, Long> MUSICDISCS_ = createField(DSL.name("musicdiscs"),
			org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>musicdiscs</code> table reference
	 */
	public Musicdiscs() {
		this(DSL.name("musicdiscs"), null);
	}

	/**
	 * Create an aliased <code>musicdiscs</code> table reference
	 */
	public Musicdiscs(String alias) {
		this(DSL.name(alias), MUSICDISCS);
	}

	/**
	 * Create an aliased <code>musicdiscs</code> table reference
	 */
	public Musicdiscs(Name alias) {
		this(alias, MUSICDISCS);
	}

	private Musicdiscs(Name alias, Table<MusicdiscsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Musicdiscs(Name alias, Table<MusicdiscsRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
	}

	public <O extends Record> Musicdiscs(Table<O> child, ForeignKey<O, MusicdiscsRecord> key) {
		super(child, key, MUSICDISCS);
	}

	@Override
	public Schema getSchema() {
		return DefaultSchema.DEFAULT_SCHEMA;
	}

	@Override
	public UniqueKey<MusicdiscsRecord> getPrimaryKey() {
		return Keys.PK_MUSICDISCS;
	}

	@Override
	public List<UniqueKey<MusicdiscsRecord>> getKeys() {
		return Arrays.<UniqueKey<MusicdiscsRecord>> asList(Keys.PK_MUSICDISCS);
	}

	@Override
	public Musicdiscs as(String alias) {
		return new Musicdiscs(DSL.name(alias), this);
	}

	@Override
	public Musicdiscs as(Name alias) {
		return new Musicdiscs(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Musicdiscs rename(String name) {
		return new Musicdiscs(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Musicdiscs rename(Name name) {
		return new Musicdiscs(name, null);
	}

	// -------------------------------------------------------------------------
	// Row2 type methods
	// -------------------------------------------------------------------------

	@Override
	public Row2<String, Long> fieldsRow() {
		return (Row2) super.fieldsRow();
	}
}
