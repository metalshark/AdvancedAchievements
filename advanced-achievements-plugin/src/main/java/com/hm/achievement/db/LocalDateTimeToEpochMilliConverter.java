package com.hm.achievement.db;

import org.jooq.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LocalDateTimeToEpochMilliConverter implements Converter<LocalDateTime, Long> {

	@Override
	public Long from(LocalDateTime databaseObject) {
		if (databaseObject == null)
			return null;
		return Timestamp.valueOf(databaseObject).getTime();
	}

	@Override
	public LocalDateTime to(Long userObject) {
		if (userObject == null)
			return null;
		return new Timestamp(userObject).toLocalDateTime();
	}

	@Override
	public Class<LocalDateTime> fromType() {
		return LocalDateTime.class;
	}

	@Override
	public Class<Long> toType() {
		return Long.class;
	}
}
