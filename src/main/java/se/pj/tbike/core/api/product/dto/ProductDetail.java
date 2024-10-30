package se.pj.tbike.core.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.attribute.dto.AttributeResponse;
import se.pj.tbike.core.api.category.dto.CategoryResponse;
import se.pj.tbike.io.ResponseType;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductDetail implements ResponseType {

	private long id;

	private String sku;

	private String name;

	private BrandResponse brand;

	private CategoryResponse category;

	private List<AttributeResponse> colors;

	private ProductSpecifications specifications;

}
