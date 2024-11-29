package se.pj.tbike.api.brand.dto;

import org.springframework.stereotype.Service;
import se.pj.tbike.api.brand.entity.Brand;

@Service
public class BrandMapper {

    public Brand map(BrandRequest req) {
        Brand brand = new Brand();
        brand.setName(req.getName());
        brand.setImageUrl(req.getImageUrl());
        brand.setDescription(req.getDescription());
        return brand;
    }

    public BrandResponse map(Brand brand) {
        BrandResponse resp = new BrandResponse();
        resp.setId(brand.getId());
        resp.setName(brand.getName());
        resp.setImageUrl(brand.getImageUrl());
        resp.setDescription(brand.getDescription());
        return resp;
    }

}
