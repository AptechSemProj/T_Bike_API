package se.pj.tbike.http.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import se.pj.tbike.http.model.attribute.AttributeRequest;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductRequest {

    private String sku;

    private String name;

    private long brandId;

    private long categoryId;

    private List<AttributeRequest> colors;

    private ProductSpecifications specifications;
}
