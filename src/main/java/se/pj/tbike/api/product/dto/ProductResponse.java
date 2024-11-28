package se.pj.tbike.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.api.brand.dto.BrandResponse;
import se.pj.tbike.api.category.dto.CategoryResponse;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private long id;

    private String sku;

    private String name;

    private BrandResponse brand;

    private CategoryResponse category;

    private String imageUrl;

    private long price;

}
