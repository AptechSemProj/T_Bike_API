package se.pj.tbike.api.core.product.category.data;

import java.time.Duration;

import se.pj.tbike.api.common.entity.IdentifiedEntity;

import se.pj.tbike.api.core.product.Product.Category;
import se.pj.tbike.api.core.product.data.ProductRepository;

import se.pj.tbike.service.CacheableCrudService;

import se.pj.tbike.util.cache.Storage;
import se.pj.tbike.util.cache.StorageManager;

public class CategoryServiceImpl
		extends CacheableCrudService<Category, Long>
		implements CategoryService {

	private static final String STORAGE_KEY = "categories";
	private static final boolean CLEANABLE = false;

	private final StorageManager manager;
	private final ProductRepository productRepository;

	public CategoryServiceImpl( CategoryRepository repository,
	                            StorageManager manager,
	                            ProductRepository productRepository ) {
		super( repository, IdentifiedEntity::getId );

		Storage<Long, Category> storage = new Storage<>(
				IdentifiedEntity::getId, Duration.ofMinutes( 1 ) );
		repository.findAll().forEach( storage::cache );
		manager.register( STORAGE_KEY, storage, CLEANABLE );
		this.manager = manager;
		this.productRepository = productRepository;
	}

	@Override
	protected Storage<Long, Category> getStorage() {
		return manager.use( STORAGE_KEY );
	}

	@Override
	public boolean remove( Long id ) {
		productRepository.deleteAllByCategoryId( id );
		return super.remove( id );
	}

	@Override
	public boolean remove( Category category ) {
		productRepository.deleteAllByCategoryId( category.getId() );
		return super.remove( category );
	}
}
