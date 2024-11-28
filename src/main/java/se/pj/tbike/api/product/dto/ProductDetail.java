package se.pj.tbike.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.api.brand.dto.BrandResponse;
import se.pj.tbike.api.attribute.dto.AttributeResponse;
import se.pj.tbike.api.category.dto.CategoryResponse;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductDetail {

	private long id;

	private String sku;

	private String name;

	private BrandResponse brand;

	private CategoryResponse category;

	private List<AttributeResponse> colors;

	private ProductSpecifications specifications;

}
