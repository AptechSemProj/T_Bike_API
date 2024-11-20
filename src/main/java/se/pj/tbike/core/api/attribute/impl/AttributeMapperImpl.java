package se.pj.tbike.core.api.attribute.impl;

import se.pj.tbike.core.api.attribute.dto.AttributeMapper;
import se.pj.tbike.core.api.attribute.dto.AttributeRequest;
import se.pj.tbike.core.api.attribute.dto.AttributeResponse;
import se.pj.tbike.core.api.attribute.entity.Attribute;

public class AttributeMapperImpl
        implements AttributeMapper {

    public Attribute map(AttributeRequest req) {
        Attribute attr = new Attribute();
        attr.setId(req.getId());
        attr.setRepresent(req.getRepresent());
        attr.setColor(req.getName());
        attr.setImageUrl(req.getImageUrl());
        attr.setPrice(req.getPrice());
        attr.setQuantity(req.getQuantity());
        return attr;
    }

    public AttributeResponse map(Attribute attr) {
        AttributeResponse resp = new AttributeResponse();
        resp.setId(attr.getId());
        resp.setName(attr.getColor());
        resp.setImageUrl(attr.getImageUrl());
        resp.setPrice(attr.getPrice());
        resp.setQuantity(attr.getQuantity());
        return resp;
    }
}
