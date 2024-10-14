package se.pj.tbike.api.core.brand.mapper;

import lombok.AllArgsConstructor;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.brand.data.BrandRepository;
import se.pj.tbike.api.core.brand.dto.BrandModification;

import se.pj.tbike.api.io.RequestMapper;

@AllArgsConstructor
public class BrandModificationMapper
		implements RequestMapper<Brand, BrandModification> {

	private final BrandRepository repository;

	@Override
	public Brand map( BrandModification req ) {
		Brand b = repository.getReferenceById( req.getId() );
		b.setName( req.getName() );
		b.setIntroduction( req.getIntroduction() );
		b.setImageUrl( req.getImageUrl() );
		return b;
	}
}
