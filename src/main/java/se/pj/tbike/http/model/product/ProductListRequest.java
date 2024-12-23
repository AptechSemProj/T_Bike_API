package se.pj.tbike.http.model.product;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.domain.Range;
import se.pj.tbike.util.PageableParameters;

@Getter
public class ProductListRequest
        extends PageableParameters {

    private final String name;

    private final Long brandId;

    private final Long categoryId;

    @Getter(AccessLevel.PRIVATE)
    private final Long minPrice;

    @Getter(AccessLevel.PRIVATE)
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

    public Range<Long> getPriceRange() {
        long min = minPrice != null ? minPrice : 0;
        long max = maxPrice != null ? maxPrice : Long.MAX_VALUE;
        return Range.closed(min, max);
    }
}
