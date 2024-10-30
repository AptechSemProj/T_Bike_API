package se.pj.tbike.core.api.brand.conf;

import org.springframework.stereotype.Service;
import se.pj.tbike.core.api.brand.dto.BrandRequest;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.brand.dto.mapper.BrandRequestMapper;
import se.pj.tbike.core.api.brand.dto.mapper.BrandResponseMapper;
import se.pj.tbike.core.api.brand.entity.Brand;

@Service
public class BrandMapper {

	public Brand map(BrandRequest req) {
		BrandRequestMapper mapper = creationMapper();
		return mapper.map( req );
	}

	public BrandResponse map(Brand brand) {
		BrandResponseMapper mapper = responseMapper();
		return mapper.map( brand );
	}

	public BrandRequestMapper creationMapper() {
		return Mappers.CREATION_MAPPER;
	}

	public BrandResponseMapper responseMapper() {
		return Mappers.RESPONSE_MAPPER;
	}

	private static final class Mappers {

		private static final BrandRequestMapper CREATION_MAPPER;
		private static final BrandResponseMapper RESPONSE_MAPPER;

		static {
			CREATION_MAPPER = new BrandRequestMapper();
			RESPONSE_MAPPER = new BrandResponseMapper();
		}
	}
}
