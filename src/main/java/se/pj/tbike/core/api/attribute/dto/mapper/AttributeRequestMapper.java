package se.pj.tbike.core.api.attribute.dto.mapper;

import se.pj.tbike.core.api.attribute.dto.AttributeRequest;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.io.RequestMapper;

public class AttributeRequestMapper
		implements RequestMapper<Attribute, AttributeRequest> {
	@Override
	public Attribute map(AttributeRequest req) {
		Attribute attribute = new Attribute();
		boolean represent = req.getRepresent() != null
				? req.getRepresent()
				: false;
		attribute.setRepresent( represent );
		attribute.setColor( req.getName() );
		attribute.setImageUrl( req.getImageUrl() );
		attribute.setPrice( req.getPrice() );
		attribute.setQuantity( req.getQuantity() );
		return attribute;
	}
}
