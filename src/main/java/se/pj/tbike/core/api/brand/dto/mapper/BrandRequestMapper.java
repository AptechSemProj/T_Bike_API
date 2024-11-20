package se.pj.tbike.core.api.brand.dto.mapper;

import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.api.brand.dto.BrandRequest;

public class BrandRequestMapper {

	public Brand map( BrandRequest req ) {
		Brand brand = new Brand();
		brand.setName( req.getName() );
		brand.setImageUrl( req.getImageUrl() );
		brand.setDescription( req.getDescription() );
		return brand;
	}
}
