package se.pj.tbike.core.api.user.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
class RoleConverter
        implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.name().toLowerCase();
    }

    @Override
    public Role convertToEntityAttribute(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        return Role.valueOf(name.toUpperCase());
    }
}
