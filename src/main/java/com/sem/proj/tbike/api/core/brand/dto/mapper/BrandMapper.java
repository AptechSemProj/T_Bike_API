package com.sem.proj.tbike.api.core.brand.dto.mapper;

import com.sem.proj.tbike.api.core.brand.Brand;
import com.sem.proj.tbike.api.core.brand.dto.BrandDTO;
import com.sem.proj.tbike.core.mapper.DTOMapper;
import org.modelmapper.ModelMapper;

public class BrandMapper
		implements DTOMapper<Brand, BrandDTO> {

	@Override
	public BrandDTO toDTO( Brand brand ) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map( brand, BrandDTO.class );
	}

	@Override
	public Brand toModel( BrandDTO brandDTO ) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map( brandDTO, Brand.class );
	}
}
