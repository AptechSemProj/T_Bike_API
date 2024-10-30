package se.pj.tbike.core.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.category.dto.CategoryResponse;
import se.pj.tbike.io.ResponseType;

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
