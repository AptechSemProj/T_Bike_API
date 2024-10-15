package se.pj.tbike.api.core.brand.mapper;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.io.ResponseMapper;
import se.pj.tbike.api.io.ResponseType;

public interface BrandResMapper<T extends ResponseType>
		extends ResponseMapper<Brand, T> {
}
