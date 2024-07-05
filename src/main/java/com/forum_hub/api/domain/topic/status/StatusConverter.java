package com.forum_hub.api.domain.topic.status;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case SOLVED:
                return true;
            case NOTSOLVED:
                return false;
            default:
                throw new IllegalArgumentException("Unknown status: " + status);
        }
    }

    @Override
    public Status convertToEntityAttribute(Boolean dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData ? Status.SOLVED : Status.NOTSOLVED;
    }
}