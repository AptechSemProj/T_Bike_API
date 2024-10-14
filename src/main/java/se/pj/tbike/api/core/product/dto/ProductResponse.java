package se.pj.tbike.api.core.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.api.core.brand.dto.BrandResponse;
import se.pj.tbike.api.core.product.category.dto.CategoryResponse;
import se.pj.tbike.api.io.ResponseType;

@Getter
@AllArgsConstructor
public class ProductResponse implements ResponseType {

	private long id;

	private String sku;

	private String name;

	private BrandResponse brand;

	private CategoryResponse category;

	private String imageUrl;

	private long price;

}
