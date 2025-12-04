package com.meetup.whatdo.user.infrastructure.converter;

import com.meetup.whatdo.user.domain.UserId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserIdConverter implements AttributeConverter<UserId, Long> {

    @Override
    public Long convertToDatabaseColumn(UserId userId) {
        return userId != null ? userId.value() : null;
    }

    @Override
    public UserId convertToEntityAttribute(Long dbData) {
        return dbData != null ? UserId.of(dbData) : null;
    }
}
