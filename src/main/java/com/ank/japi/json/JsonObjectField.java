package com.ank.japi.json;

import java.util.*;

public final class JsonObjectField
        implements JsonField {

    private static final int HASH_MAP_LIMITED = 10;

    private final String name;
    private final boolean nullable;
    private boolean modifiable = true;
    private Map<String, JsonField> structure;

    public JsonObjectField(String name, boolean nullable, Set<JsonField> structure) {
        this.name = Objects.requireNonNull(name);
        this.nullable = nullable;
        this.structure = new HashMap<>(structure.size());
        for (JsonField jsonField : structure) {
            this.structure.put(jsonField.getName(), jsonField);
        }
    }

    public JsonObjectField(String name, boolean nullable) {
        this(name, nullable, Set.of());
    }

    public JsonObjectField(String name, Set<JsonField> structure) {
        this(name, true, structure);
    }

    public JsonObjectField(String name) {
        this(name, true);
    }

    private void recreateStructure() {
        if (!modifiable) {
            return;
        }
        if (structure instanceof LinkedHashMap) {
            return;
        }
        if (structure.size() > HASH_MAP_LIMITED) {
            structure = new LinkedHashMap<>(structure);
        }
    }

    public void addField(JsonField field) {
        if (!modifiable) {
            return;
        }
        structure.put(field.getName(), field);
        recreateStructure();
    }

    public void setModifiable(boolean flag) {
        modifiable = flag;
        if (modifiable) {
            if (structure.size() < HASH_MAP_LIMITED) {
                structure = new HashMap<>(structure);
            } else {
                structure = new LinkedHashMap<>(structure);
            }
        } else {
            this.structure = Map.copyOf(structure);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public JsonObject value() {
        return new JsonObject(structure, nullable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonObjectField that)) {
            return false;
        }
        return nullable == that.nullable
                && modifiable == that.modifiable
                && Objects.equals(name, that.name)
                && Objects.equals(structure, that.structure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nullable, modifiable, structure);
    }
}
