package se.pj.tbike.http.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.http.model.brand.BrandResponse;
import se.pj.tbike.http.model.attribute.AttributeResponse;
import se.pj.tbike.http.model.category.CategoryResponse;

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
