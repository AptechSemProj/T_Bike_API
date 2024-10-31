package se.pj.tbike.core.api.attribute.conf;

import org.springframework.stereotype.Service;
import se.pj.tbike.core.api.attribute.dto.AttributeRequest;
import se.pj.tbike.core.api.attribute.dto.AttributeResponse;
import se.pj.tbike.core.api.attribute.dto.mapper.AttributeRequestMapper;
import se.pj.tbike.core.api.attribute.dto.mapper.AttributeResponseMapper;
import se.pj.tbike.core.api.attribute.entity.Attribute;

@Service
public class AttributeMapper {

	public Attribute map(AttributeRequest req) {
		return requestMapper().map( req );
	}

	public AttributeResponse map(Attribute a) {
		return responseMapper().map( a );
	}

	public AttributeResponseMapper responseMapper() {
		return Mappers.RESPONSE_MAPPER;
	}

	public AttributeRequestMapper requestMapper() {
		return Mappers.REQUEST_MAPPER;
	}

	private static final class Mappers {
		public static final AttributeRequestMapper REQUEST_MAPPER =
				new AttributeRequestMapper();

		public static final AttributeResponseMapper RESPONSE_MAPPER =
				new AttributeResponseMapper();

	}
}
