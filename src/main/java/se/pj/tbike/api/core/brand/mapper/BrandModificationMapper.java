package se.pj.tbike.api.core.brand.mapper;

import lombok.AllArgsConstructor;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.data.BrandRepository;
import se.pj.tbike.api.core.brand.dto.BrandModification;

import java.util.Optional;

@AllArgsConstructor
public class BrandModificationMapper
		implements BrandReqMapper<BrandModification> {

	private final BrandRepository repository;

	@Override
	public Brand map( BrandModification req ) {
		Optional<Brand> b = repository.findById( req.getId() );
		if ( b.isPresent() ) {
			b.get().setName( req.getName() );
			b.get().setIntroduction( req.getIntroduction() );
			b.get().setImageUrl( req.getImageUrl() );
		}
		return b.orElse( null );
	}
}
