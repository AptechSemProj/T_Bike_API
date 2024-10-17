package se.pj.tbike.api.core.brand.data;

import java.time.Duration;

import se.pj.tbike.api.common.entity.IdentifiedEntity;

import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.api.core.product.data.ProductRepository;

import se.pj.tbike.service.CacheableCrudService;

import se.pj.tbike.util.cache.Storage;
import se.pj.tbike.util.cache.StorageManager;

public class BrandServiceImpl
		extends CacheableCrudService<Brand, Long>
		implements BrandService {

	private static final String STORAGE_KEY = "brands";
	private static final boolean CLEANABLE = false;

	private final StorageManager manager;
	private final ProductRepository productRepository;

	public BrandServiceImpl( BrandRepository repository,
	                         StorageManager manager,
	                         ProductRepository productRepository ) {
		super( repository, IdentifiedEntity::getId );

		Storage<Long, Brand> storage = new Storage<>(
				IdentifiedEntity::getId, Duration.ofMinutes( 1 ) );
		repository.findAll().forEach( storage::cache );
		manager.register( STORAGE_KEY, storage, CLEANABLE );
		this.manager = manager;
		this.productRepository = productRepository;
	}

	@Override
	protected Storage<Long, Brand> getStorage() {
		return manager.use( STORAGE_KEY );
	}

	@Override
	public boolean remove( Long id ) {
		productRepository.deleteAllByBrandId( id );
		return super.remove( id );
	}

	@Override
	public boolean remove( Brand brand ) {
		productRepository.deleteAllByBrandId( brand.getId() );
		return super.remove( brand );
	}
}
