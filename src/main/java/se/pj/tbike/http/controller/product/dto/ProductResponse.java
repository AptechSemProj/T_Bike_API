package se.pj.tbike.http.controller.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.http.model.brand.BrandResponse;
import se.pj.tbike.http.model.category.CategoryResponse;

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
