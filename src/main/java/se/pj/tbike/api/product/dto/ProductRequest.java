package se.pj.tbike.api.product.dto;

import com.ank.japi.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import se.pj.tbike.api.attribute.dto.AttributeRequest;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductRequest
        implements Request<Long> {

    private String sku;

    private String name;

    private long brandId;

    private long categoryId;

    private List<AttributeRequest> colors;

    private ProductSpecifications specifications;
}
