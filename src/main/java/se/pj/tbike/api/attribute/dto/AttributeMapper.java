package se.pj.tbike.api.attribute.dto;

import org.springframework.stereotype.Service;
import se.pj.tbike.api.attribute.entity.Attribute;

@Service
public class AttributeMapper {

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
