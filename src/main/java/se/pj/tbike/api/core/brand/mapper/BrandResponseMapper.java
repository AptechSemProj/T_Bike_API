package se.pj.tbike.api.core.brand.mapper;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.dto.BrandResponse;

public class BrandResponseMapper
		implements BrandResMapper<BrandResponse> {

	@Override
	public BrandResponse map( Brand brand ) {
		return new BrandResponse( brand.getId(), brand.getName(),
				brand.getIntroduction(), brand.getImageUrl() );
	}
}
