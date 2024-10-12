package com.sem.proj.tbike.config;

import com.sem.proj.tbike.api.core.brand.Brand;
import com.sem.proj.tbike.api.core.brand.dto.BrandDTO;
import com.sem.proj.tbike.api.core.brand.dto.mapper.BrandMapper;
import com.sem.proj.tbike.core.mapper.DTOMapper;
import org.springframework.context.annotation.Bean;

public class Mapper {

	@Bean
	public DTOMapper<Brand, BrandDTO> brandMapper() {
		return new BrandMapper();
	}
}
