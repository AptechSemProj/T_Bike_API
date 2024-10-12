package com.sem.proj.tbike.api.core.product.dto;

import com.sem.proj.tbike.api.core.product.Product;
import com.sem.proj.tbike.core.mapper.DTOMapper;

public class ProductMapper
		implements DTOMapper<Product, ProductDTO> {

	public ProductMapper() {
	}

	@Override
	public ProductDTO toDTO( Product product ) {

		return null;
	}

	@Override
	public Product toModel( ProductDTO productDTO ) {
		return null;
	}
}
