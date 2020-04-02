package com.hm.achievement.db;

import org.jooq.Converter;

import java.util.UUID;

public class StringToUUIDConverter implements Converter<String, UUID> {

	@Override
	public UUID from(String databaseObject) {
		if (databaseObject == null) return null;
		return UUID.fromString(databaseObject);
	}

	@Override
	public String to(UUID userObject) {
		if (userObject == null) return null;
		return userObject.toString();
	}

	@Override
	public Class<String> fromType() {
		return String.class;
	}

	@Override
	public Class<UUID> toType() {
		return UUID.class;
	}
}
