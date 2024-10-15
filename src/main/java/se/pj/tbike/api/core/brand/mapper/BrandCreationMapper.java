package se.pj.tbike.api.core.brand.mapper;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.dto.BrandCreation;

public class BrandCreationMapper
		implements BrandReqMapper<BrandCreation> {

	@Override
	public Brand map( BrandCreation req ) {
		return new Brand(
				req.getName(), req.getIntroduction(), req.getImageUrl() );
	}
}
