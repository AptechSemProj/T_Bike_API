package se.pj.tbike.core.api.brand.dto;

import se.pj.tbike.core.api.brand.entity.Brand;

public interface BrandMapper {

    Brand map(BrandRequest req);

    BrandResponse map(Brand brand);

}
