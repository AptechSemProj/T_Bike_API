package se.pj.tbike.core.api.attribute.dto.mapper;

import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.api.attribute.dto.AttributeResponse;
import se.pj.tbike.io.ResponseMapper;

public class AttributeResponseMapper
		implements ResponseMapper<Attribute, AttributeResponse> {

	@Override
	public AttributeResponse map(Attribute attr ) {
		return new AttributeResponse( attr.getId(), attr.getColor(),
				attr.getImageUrl(), attr.getPrice(), attr.getQuantity() );
	}
}
