/*
 * This file is generated by jOOQ.
 */
package com.hm.achievement.db.tables.records;

import com.hm.achievement.db.tables.Connections;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConnectionsRecord extends UpdatableRecordImpl<ConnectionsRecord> implements Record3<String, Integer, String> {

	private static final long serialVersionUID = 1;

	/**
	 * Setter for <code>connections.playername</code>.
	 */
	public void setPlayername(String value) {
		set(0, value);
	}

	/**
	 * Getter for <code>connections.playername</code>.
	 */
	public String getPlayername() {
		return (String) get(0);
	}

	/**
	 * Setter for <code>connections.connections</code>.
	 */
	public void setConnections(Integer value) {
		set(1, value);
	}

	/**
	 * Getter for <code>connections.connections</code>.
	 */
	public Integer getConnections() {
		return (Integer) get(1);
	}

	/**
	 * Setter for <code>connections.date</code>.
	 */
	public void setDate(String value) {
		set(2, value);
	}

	/**
	 * Getter for <code>connections.date</code>.
	 */
	public String getDate() {
		return (String) get(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	@Override
	public Record1<String> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	@Override
	public Row3<String, Integer, String> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	@Override
	public Row3<String, Integer, String> valuesRow() {
		return (Row3) super.valuesRow();
	}

	@Override
	public Field<String> field1() {
		return Connections.CONNECTIONS.PLAYERNAME;
	}

	@Override
	public Field<Integer> field2() {
		return Connections.CONNECTIONS.CONNECTIONS_;
	}

	@Override
	public Field<String> field3() {
		return Connections.CONNECTIONS.DATE;
	}

	@Override
	public String component1() {
		return getPlayername();
	}

	@Override
	public Integer component2() {
		return getConnections();
	}

	@Override
	public String component3() {
		return getDate();
	}

	@Override
	public String value1() {
		return getPlayername();
	}

	@Override
	public Integer value2() {
		return getConnections();
	}

	@Override
	public String value3() {
		return getDate();
	}

	@Override
	public ConnectionsRecord value1(String value) {
		setPlayername(value);
		return this;
	}

	@Override
	public ConnectionsRecord value2(Integer value) {
		setConnections(value);
		return this;
	}

	@Override
	public ConnectionsRecord value3(String value) {
		setDate(value);
		return this;
	}

	@Override
	public ConnectionsRecord values(String value1, Integer value2, String value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ConnectionsRecord
	 */
	public ConnectionsRecord() {
		super(Connections.CONNECTIONS);
	}

	/**
	 * Create a detached, initialised ConnectionsRecord
	 */
	public ConnectionsRecord(String playername, Integer connections, String date) {
		super(Connections.CONNECTIONS);

		set(0, playername);
		set(1, connections);
		set(2, date);
	}
}
