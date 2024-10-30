package se.pj.tbike.core.api.brand.dto.mapper;

import se.pj.tbike.core.api.brand.dto.BrandResponse.Builder;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.io.ResponseMapper;

public class BrandResponseMapper
		implements ResponseMapper<Brand, BrandResponse> {

	@Override
	public BrandResponse map(Brand brand) {
		Builder builder = BrandResponse.builder();
		builder.id( brand.getId() );
		builder.name( brand.getName() );
		builder.imageUrl( brand.getImageUrl() );
		builder.description( brand.getDescription() );
		return builder.build();
	}
}
