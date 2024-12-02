package se.pj.tbike.http.model.brand;

import org.springframework.stereotype.Service;
import se.pj.tbike.domain.entity.Brand;

@Service
public class BrandMapper {

    public Brand map(CreateBrandRequest req) {
        Brand brand = new Brand();
        brand.setName(req.getName());
        brand.setImageUrl(req.getImageUrl());
        brand.setDescription(req.getDescription());
        return brand;
    }

    public Brand map(UpdateBrandRequest req) {
        Brand brand = map((CreateBrandRequest) req);
        brand.setId(req.getId());
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
