package se.pj.tbike.core.api.attribute.dto;

import se.pj.tbike.core.api.attribute.entity.Attribute;

public interface AttributeMapper {

    Attribute map(AttributeRequest req);

    AttributeResponse map(Attribute attr);

}
