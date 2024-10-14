package se.pj.tbike.api.core.brand.mapper;

import lombok.RequiredArgsConstructor;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.dto.BrandResponse;
import se.pj.tbike.api.io.ResponseMapper;

@RequiredArgsConstructor
public class BrandResponseMapper
		implements ResponseMapper<Brand, BrandResponse> {

	@Override
	public BrandResponse map( Brand brand ) {
		return new BrandResponse(
				brand.getId(), brand.getName(), brand.getIntroduction() );
	}
}
