/*
 * This file is generated by jOOQ.
 */
package com.hm.achievement.db.tables.records;

import com.hm.achievement.db.tables.Distancefoot;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DistancefootRecord extends UpdatableRecordImpl<DistancefootRecord> implements Record2<String, Long> {

	private static final long serialVersionUID = 872743162;

	/**
	 * Setter for <code>distancefoot.playername</code>.
	 */
	public void setPlayername(String value) {
		set(0, value);
	}

	/**
	 * Getter for <code>distancefoot.playername</code>.
	 */
	public String getPlayername() {
		return (String) get(0);
	}

	/**
	 * Setter for <code>distancefoot.distancefoot</code>.
	 */
	public void setDistancefoot(Long value) {
		set(1, value);
	}

	/**
	 * Getter for <code>distancefoot.distancefoot</code>.
	 */
	public Long getDistancefoot() {
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
		return Distancefoot.DISTANCEFOOT.PLAYERNAME;
	}

	@Override
	public Field<Long> field2() {
		return Distancefoot.DISTANCEFOOT.DISTANCEFOOT_;
	}

	@Override
	public String component1() {
		return getPlayername();
	}

	@Override
	public Long component2() {
		return getDistancefoot();
	}

	@Override
	public String value1() {
		return getPlayername();
	}

	@Override
	public Long value2() {
		return getDistancefoot();
	}

	@Override
	public DistancefootRecord value1(String value) {
		setPlayername(value);
		return this;
	}

	@Override
	public DistancefootRecord value2(Long value) {
		setDistancefoot(value);
		return this;
	}

	@Override
	public DistancefootRecord values(String value1, Long value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DistancefootRecord
	 */
	public DistancefootRecord() {
		super(Distancefoot.DISTANCEFOOT);
	}

	/**
	 * Create a detached, initialised DistancefootRecord
	 */
	public DistancefootRecord(String playername, Long distancefoot) {
		super(Distancefoot.DISTANCEFOOT);

		set(0, playername);
		set(1, distancefoot);
	}
}
