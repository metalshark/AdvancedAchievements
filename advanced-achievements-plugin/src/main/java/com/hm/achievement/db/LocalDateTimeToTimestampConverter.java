package com.hm.achievement.db;

import org.jooq.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LocalDateTimeToTimestampConverter implements Converter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp from(LocalDateTime databaseObject) {
		if (databaseObject == null) return null;
		return Timestamp.valueOf(databaseObject);
	}

	@Override
	public LocalDateTime to(Timestamp userObject) {
		if (userObject == null) return null;
		return userObject.toLocalDateTime();
	}

	@Override
	public Class<LocalDateTime> fromType() {
		return LocalDateTime.class;
	}

	@Override
	public Class<Timestamp> toType() {
		return Timestamp.class;
	}
}
