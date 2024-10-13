package com.sem.proj.tbike.api.core.product.dto;

import com.sem.proj.tbike.api.core.brand.dto.BrandDTO;
import com.sem.proj.tbike.api.core.product.Product;
import com.sem.proj.tbike.api.util.ResponseType;
import com.sem.proj.tbike.core.mapper.DTOMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ResponseType
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBasicInfo {

	private long id;

	private String sku;

	private String name;

	private String imageUrl;

	private long price;

	private BrandDTO brand;

	private CategoryDTO category;

	public static class Mapper
			implements DTOMapper<Product, ProductBasicInfo> {

		public Mapper() {
		}

		@Override
		public ProductBasicInfo toDTO( Product product ) {

			return null;
		}

		@Override
		public Product toModel( ProductBasicInfo dto ) {
			return null;
		}
	}
}
