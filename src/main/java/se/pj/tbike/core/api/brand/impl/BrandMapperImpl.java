package se.pj.tbike.core.api.brand.impl;

import se.pj.tbike.core.api.brand.dto.BrandMapper;
import se.pj.tbike.core.api.brand.dto.BrandRequest;
import se.pj.tbike.core.api.brand.dto.BrandResponse;
import se.pj.tbike.core.api.brand.entity.Brand;

public class BrandMapperImpl
        implements BrandMapper {

    @Override
    public Brand map(BrandRequest req) {
        Brand brand = new Brand();
        brand.setName(req.getName());
        brand.setImageUrl(req.getImageUrl());
        brand.setDescription(req.getDescription());
        return brand;
    }

    @Override
    public BrandResponse map(Brand brand) {
        BrandResponse resp = new BrandResponse();
        resp.setId(brand.getId());
        resp.setName(brand.getName());
        resp.setImageUrl(brand.getImageUrl());
        resp.setDescription(brand.getDescription());
        return resp;
    }

}
