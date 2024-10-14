package se.pj.tbike.api.core.product.mapper;

import se.pj.tbike.api.core.product.Product.Attribute;
import se.pj.tbike.api.core.product.dto.AttributeResponse;
import se.pj.tbike.api.io.ResponseMapper;

public class AttributeResponseMapper
		implements ResponseMapper<Attribute, AttributeResponse> {
	@Override
	public AttributeResponse map( Attribute attr ) {
		return new AttributeResponse( attr.getId(), attr.getColor(),
				attr.getImageUrl(), attr.getPrice(), attr.getQuantity() );
	}
}
