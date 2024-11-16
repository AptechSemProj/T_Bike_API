package se.pj.tbike.core.api.product.dto;

import com.ank.japi.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import se.pj.tbike.core.api.attribute.dto.AttributeRequest;
import se.pj.tbike.io.RequestType;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductRequest
        implements RequestType,
                   Request<Long> {

    private String sku;

    private String name;

    private long brandId;

    private long categoryId;

    private List<AttributeRequest> colors;

    private ProductSpecifications specifications;
}
