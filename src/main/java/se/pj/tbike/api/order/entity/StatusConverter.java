package se.pj.tbike.api.order.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
class StatusConverter
        implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status.name().toLowerCase();
    }

    @Override
    public Status convertToEntityAttribute(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return Status.valueOf(value.toUpperCase());
    }
}
