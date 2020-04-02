/*
 * This file is generated by jOOQ.
 */
package com.hm.achievement.db.tables.records;

import com.hm.achievement.db.tables.Anvils;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AnvilsRecord extends UpdatableRecordImpl<AnvilsRecord> implements Record2<String, Long> {

	private static final long serialVersionUID = 525046370;

	/**
	 * Setter for <code>anvils.playername</code>.
	 */
	public void setPlayername(String value) {
		set(0, value);
	}

	/**
	 * Getter for <code>anvils.playername</code>.
	 */
	public String getPlayername() {
		return (String) get(0);
	}

	/**
	 * Setter for <code>anvils.anvils</code>.
	 */
	public void setAnvils(Long value) {
		set(1, value);
	}

	/**
	 * Getter for <code>anvils.anvils</code>.
	 */
	public Long getAnvils() {
		return (Long) get(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	@Override
	public Record1<String> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	@Override
	public Row2<String, Long> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	@Override
	public Row2<String, Long> valuesRow() {
		return (Row2) super.valuesRow();
	}

	@Override
	public Field<String> field1() {
		return Anvils.ANVILS.PLAYERNAME;
	}

	@Override
	public Field<Long> field2() {
		return Anvils.ANVILS.ANVILS_;
	}

	@Override
	public String component1() {
		return getPlayername();
	}

	@Override
	public Long component2() {
		return getAnvils();
	}

	@Override
	public String value1() {
		return getPlayername();
	}

	@Override
	public Long value2() {
		return getAnvils();
	}

	@Override
	public AnvilsRecord value1(String value) {
		setPlayername(value);
		return this;
	}

	@Override
	public AnvilsRecord value2(Long value) {
		setAnvils(value);
		return this;
	}

	@Override
	public AnvilsRecord values(String value1, Long value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AnvilsRecord
	 */
	public AnvilsRecord() {
		super(Anvils.ANVILS);
	}

	/**
	 * Create a detached, initialised AnvilsRecord
	 */
	public AnvilsRecord(String playername, Long anvils) {
		super(Anvils.ANVILS);

		set(0, playername);
		set(1, anvils);
	}
}
