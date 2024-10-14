package se.pj.tbike.api.core.brand.data;

import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.service.impl.CrudServiceImpl;
import se.pj.tbike.util.cache.Storage;

public class BrandServiceImpl
		extends CrudServiceImpl<Brand, Long>
		implements BrandService {

	public BrandServiceImpl( BrandRepository repository,
	                         Storage<Long, Brand> storage ) {
		super( repository, IdentifiedEntity::getId, storage );
	}
}
