package com.sem.proj.tbike.api.core.product.dto;

import com.sem.proj.tbike.api.core.brand.dto.BrandDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
