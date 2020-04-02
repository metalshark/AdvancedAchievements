/*
 * This file is generated by jOOQ.
 */
package com.hm.achievement.db.tables.records;

import com.hm.achievement.db.tables.Distancehorse;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DistancehorseRecord extends UpdatableRecordImpl<DistancehorseRecord> implements Record2<String, Long> {

	private static final long serialVersionUID = 2116076274;

	/**
	 * Setter for <code>distancehorse.playername</code>.
	 */
	public void setPlayername(String value) {
		set(0, value);
	}

	/**
	 * Getter for <code>distancehorse.playername</code>.
	 */
	public String getPlayername() {
		return (String) get(0);
	}

	/**
	 * Setter for <code>distancehorse.distancehorse</code>.
	 */
	public void setDistancehorse(Long value) {
		set(1, value);
	}

	/**
	 * Getter for <code>distancehorse.distancehorse</code>.
	 */
	public Long getDistancehorse() {
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
		return Distancehorse.DISTANCEHORSE.PLAYERNAME;
	}

	@Override
	public Field<Long> field2() {
		return Distancehorse.DISTANCEHORSE.DISTANCEHORSE_;
	}

	@Override
	public String component1() {
		return getPlayername();
	}

	@Override
	public Long component2() {
		return getDistancehorse();
	}

	@Override
	public String value1() {
		return getPlayername();
	}

	@Override
	public Long value2() {
		return getDistancehorse();
	}

	@Override
	public DistancehorseRecord value1(String value) {
		setPlayername(value);
		return this;
	}

	@Override
	public DistancehorseRecord value2(Long value) {
		setDistancehorse(value);
		return this;
	}

	@Override
	public DistancehorseRecord values(String value1, Long value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DistancehorseRecord
	 */
	public DistancehorseRecord() {
		super(Distancehorse.DISTANCEHORSE);
	}

	/**
	 * Create a detached, initialised DistancehorseRecord
	 */
	public DistancehorseRecord(String playername, Long distancehorse) {
		super(Distancehorse.DISTANCEHORSE);

		set(0, playername);
		set(1, distancehorse);
	}
}
