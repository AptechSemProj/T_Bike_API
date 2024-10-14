package se.pj.tbike.api.core.brand.mapper;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.dto.BrandCreation;
import se.pj.tbike.api.io.RequestMapper;

public class BrandCreationMapper
		implements RequestMapper<Brand, BrandCreation> {

	@Override
	public Brand map( BrandCreation req ) {
		return new Brand( req.getName(), req.getIntroduction() );
	}
}
