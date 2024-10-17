package se.pj.tbike.api.core.brand.mapper;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.data.BrandRepository;
import se.pj.tbike.api.core.brand.dto.BrandModification;

@RequiredArgsConstructor
public class BrandModificationMapper
		implements BrandReqMapper<BrandModification> {

	private final BrandRepository repository;

	@Override
	public Brand map( BrandModification req ) {
		Optional<Brand> o = repository.findById( req.getId() );
		if ( o.isPresent() ) {
			Brand b = o.get();
			b.setName( req.getName() );
			b.setIntroduction( req.getIntroduction() );
			b.setImageUrl( req.getImageUrl() );
			return b;
		}
		return null;
	}
}
