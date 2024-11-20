package se.pj.tbike.core.api.product.dto;

import lombok.Getter;
import se.pj.tbike.core.util.PageableParameters;
import se.pj.tbike.io.RequestType;

@Getter
public class ProductListRequest
        extends PageableParameters
        implements RequestType {

    private final String name;

    private final Long brandId;

    private final Long categoryId;

    private final Long minPrice;

    private final Long maxPrice;

//    private
    // order by price

    public ProductListRequest(String page, String size,
                              String name, Long brandId, Long categoryId,
                              Long minPrice, Long maxPrice) {
        super(page, size);
        this.name = name;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
