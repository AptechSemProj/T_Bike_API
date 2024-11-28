package se.pj.tbike.api.brand.dto;

import se.pj.tbike.api.brand.entity.Brand;

public interface BrandMapper {

    Brand map(BrandRequest req);

    BrandResponse map(Brand brand);

}
