/*
 * This file is generated by jOOQ.
 */
package com.hm.achievement.db.tables;

import com.hm.achievement.db.DefaultSchema;
import com.hm.achievement.db.Keys;
import com.hm.achievement.db.tables.records.DistancepigRecord;

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
public class Distancepig extends TableImpl<DistancepigRecord> {

	private static final long serialVersionUID = -1779320016;

	/**
	 * The reference instance of <code>distancepig</code>
	 */
	public static final Distancepig DISTANCEPIG = new Distancepig();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<DistancepigRecord> getRecordType() {
		return DistancepigRecord.class;
	}

	/**
	 * The column <code>distancepig.playername</code>.
	 */
	public final TableField<DistancepigRecord, String> PLAYERNAME = createField(DSL.name("playername"),
			org.jooq.impl.SQLDataType.CHAR(36), this, "");

	/**
	 * The column <code>distancepig.distancepig</code>.
	 */
	public final TableField<DistancepigRecord, Long> DISTANCEPIG_ = createField(DSL.name("distancepig"),
			org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>distancepig</code> table reference
	 */
	public Distancepig() {
		this(DSL.name("distancepig"), null);
	}

	/**
	 * Create an aliased <code>distancepig</code> table reference
	 */
	public Distancepig(String alias) {
		this(DSL.name(alias), DISTANCEPIG);
	}

	/**
	 * Create an aliased <code>distancepig</code> table reference
	 */
	public Distancepig(Name alias) {
		this(alias, DISTANCEPIG);
	}

	private Distancepig(Name alias, Table<DistancepigRecord> aliased) {
		this(alias, aliased, null);
	}

	private Distancepig(Name alias, Table<DistancepigRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
	}

	public <O extends Record> Distancepig(Table<O> child, ForeignKey<O, DistancepigRecord> key) {
		super(child, key, DISTANCEPIG);
	}

	@Override
	public Schema getSchema() {
		return DefaultSchema.DEFAULT_SCHEMA;
	}

	@Override
	public UniqueKey<DistancepigRecord> getPrimaryKey() {
		return Keys.PK_DISTANCEPIG;
	}

	@Override
	public List<UniqueKey<DistancepigRecord>> getKeys() {
		return Arrays.<UniqueKey<DistancepigRecord>> asList(Keys.PK_DISTANCEPIG);
	}

	@Override
	public Distancepig as(String alias) {
		return new Distancepig(DSL.name(alias), this);
	}

	@Override
	public Distancepig as(Name alias) {
		return new Distancepig(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Distancepig rename(String name) {
		return new Distancepig(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Distancepig rename(Name name) {
		return new Distancepig(name, null);
	}

	// -------------------------------------------------------------------------
	// Row2 type methods
	// -------------------------------------------------------------------------

	@Override
	public Row2<String, Long> fieldsRow() {
		return (Row2) super.fieldsRow();
	}
}
