/*
 * This file is generated by jOOQ.
 */
package com.hm.achievement.db.tables;

import com.hm.achievement.db.DefaultSchema;
import com.hm.achievement.db.Keys;
import com.hm.achievement.db.tables.records.DistancefootRecord;

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
public class Distancefoot extends TableImpl<DistancefootRecord> {

	private static final long serialVersionUID = -262293282;

	/**
	 * The reference instance of <code>distancefoot</code>
	 */
	public static final Distancefoot DISTANCEFOOT = new Distancefoot();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<DistancefootRecord> getRecordType() {
		return DistancefootRecord.class;
	}

	/**
	 * The column <code>distancefoot.playername</code>.
	 */
	public final TableField<DistancefootRecord, String> PLAYERNAME = createField(DSL.name("playername"),
			org.jooq.impl.SQLDataType.CHAR(36), this, "");

	/**
	 * The column <code>distancefoot.distancefoot</code>.
	 */
	public final TableField<DistancefootRecord, Long> DISTANCEFOOT_ = createField(DSL.name("distancefoot"),
			org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>distancefoot</code> table reference
	 */
	public Distancefoot() {
		this(DSL.name("distancefoot"), null);
	}

	/**
	 * Create an aliased <code>distancefoot</code> table reference
	 */
	public Distancefoot(String alias) {
		this(DSL.name(alias), DISTANCEFOOT);
	}

	/**
	 * Create an aliased <code>distancefoot</code> table reference
	 */
	public Distancefoot(Name alias) {
		this(alias, DISTANCEFOOT);
	}

	private Distancefoot(Name alias, Table<DistancefootRecord> aliased) {
		this(alias, aliased, null);
	}

	private Distancefoot(Name alias, Table<DistancefootRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
	}

	public <O extends Record> Distancefoot(Table<O> child, ForeignKey<O, DistancefootRecord> key) {
		super(child, key, DISTANCEFOOT);
	}

	@Override
	public Schema getSchema() {
		return DefaultSchema.DEFAULT_SCHEMA;
	}

	@Override
	public UniqueKey<DistancefootRecord> getPrimaryKey() {
		return Keys.PK_DISTANCEFOOT;
	}

	@Override
	public List<UniqueKey<DistancefootRecord>> getKeys() {
		return Arrays.<UniqueKey<DistancefootRecord>> asList(Keys.PK_DISTANCEFOOT);
	}

	@Override
	public Distancefoot as(String alias) {
		return new Distancefoot(DSL.name(alias), this);
	}

	@Override
	public Distancefoot as(Name alias) {
		return new Distancefoot(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Distancefoot rename(String name) {
		return new Distancefoot(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Distancefoot rename(Name name) {
		return new Distancefoot(name, null);
	}

	// -------------------------------------------------------------------------
	// Row2 type methods
	// -------------------------------------------------------------------------

	@Override
	public Row2<String, Long> fieldsRow() {
		return (Row2) super.fieldsRow();
	}
}
