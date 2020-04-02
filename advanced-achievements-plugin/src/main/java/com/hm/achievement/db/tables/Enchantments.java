/*
 * This file is generated by jOOQ.
 */
package com.hm.achievement.db.tables;

import com.hm.achievement.db.DefaultSchema;
import com.hm.achievement.db.Keys;
import com.hm.achievement.db.tables.records.EnchantmentsRecord;

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
public class Enchantments extends TableImpl<EnchantmentsRecord> {

	private static final long serialVersionUID = -404753979;

	/**
	 * The reference instance of <code>enchantments</code>
	 */
	public static final Enchantments ENCHANTMENTS = new Enchantments();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<EnchantmentsRecord> getRecordType() {
		return EnchantmentsRecord.class;
	}

	/**
	 * The column <code>enchantments.playername</code>.
	 */
	public final TableField<EnchantmentsRecord, String> PLAYERNAME = createField(DSL.name("playername"),
			org.jooq.impl.SQLDataType.CHAR(36), this, "");

	/**
	 * The column <code>enchantments.enchantments</code>.
	 */
	public final TableField<EnchantmentsRecord, Long> ENCHANTMENTS_ = createField(DSL.name("enchantments"),
			org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>enchantments</code> table reference
	 */
	public Enchantments() {
		this(DSL.name("enchantments"), null);
	}

	/**
	 * Create an aliased <code>enchantments</code> table reference
	 */
	public Enchantments(String alias) {
		this(DSL.name(alias), ENCHANTMENTS);
	}

	/**
	 * Create an aliased <code>enchantments</code> table reference
	 */
	public Enchantments(Name alias) {
		this(alias, ENCHANTMENTS);
	}

	private Enchantments(Name alias, Table<EnchantmentsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Enchantments(Name alias, Table<EnchantmentsRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
	}

	public <O extends Record> Enchantments(Table<O> child, ForeignKey<O, EnchantmentsRecord> key) {
		super(child, key, ENCHANTMENTS);
	}

	@Override
	public Schema getSchema() {
		return DefaultSchema.DEFAULT_SCHEMA;
	}

	@Override
	public UniqueKey<EnchantmentsRecord> getPrimaryKey() {
		return Keys.PK_ENCHANTMENTS;
	}

	@Override
	public List<UniqueKey<EnchantmentsRecord>> getKeys() {
		return Arrays.<UniqueKey<EnchantmentsRecord>> asList(Keys.PK_ENCHANTMENTS);
	}

	@Override
	public Enchantments as(String alias) {
		return new Enchantments(DSL.name(alias), this);
	}

	@Override
	public Enchantments as(Name alias) {
		return new Enchantments(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Enchantments rename(String name) {
		return new Enchantments(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Enchantments rename(Name name) {
		return new Enchantments(name, null);
	}

	// -------------------------------------------------------------------------
	// Row2 type methods
	// -------------------------------------------------------------------------

	@Override
	public Row2<String, Long> fieldsRow() {
		return (Row2) super.fieldsRow();
	}
}
