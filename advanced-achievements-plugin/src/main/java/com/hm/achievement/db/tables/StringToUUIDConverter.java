package com.hm.achievement.db.tables;

import org.jooq.Converter;

import java.util.UUID;

public class StringToUUIDConverter implements Converter<String, UUID> {
    @Override
    public UUID from(String databaseObject) {
        return UUID.fromString(databaseObject);
    }

    @Override
    public String to(UUID userObject) {
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
