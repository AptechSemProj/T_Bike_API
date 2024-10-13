package com.sem.proj.tbike.api.core.brand.dto;

import com.sem.proj.tbike.api.core.brand.Brand;
import com.sem.proj.tbike.api.util.ResponseType;
import com.sem.proj.tbike.core.mapper.DTOMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@ResponseType
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

	private long id;

	private String name;

	private String introduction;

	public static class Mapper
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
}
