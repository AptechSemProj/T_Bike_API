package se.pj.tbike.api.core.brand.mapper;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.io.RequestMapper;
import se.pj.tbike.api.io.RequestType;

public interface BrandReqMapper<T extends RequestType>
		extends RequestMapper<Brand, T> {
}
