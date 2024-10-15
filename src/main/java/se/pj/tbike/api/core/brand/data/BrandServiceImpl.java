package se.pj.tbike.api.core.brand.data;

import java.util.List;
import java.util.Optional;

import java.time.Duration;

import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.brand.Brand;
import se.pj.tbike.service.StdCrudService;
import se.pj.tbike.util.cache.Storage;
import se.pj.tbike.util.cache.StorageManager;
import se.pj.tbike.util.result.Result;
import se.pj.tbike.util.result.ResultImpl;
import se.pj.tbike.util.result.ResultList;
import se.pj.tbike.util.result.ResultListImpl;
import se.pj.tbike.util.result.ResultPage;
import se.pj.tbike.util.result.ResultPageImpl;

public class BrandServiceImpl
		extends StdCrudService<Brand, Long>
		implements BrandService {

	private static final String STORAGE_KEY = "brands";
	private static final boolean CLEANABLE = false;

	private final StorageManager manager;

	public BrandServiceImpl( BrandRepository repository,
	                         StorageManager manager ) {
		super( repository, IdentifiedEntity::getId );

		Storage<Long, Brand> storage = new Storage<>(
				IdentifiedEntity::getId, Duration.ofMinutes( 1 ) );
		repository.findAll().forEach( storage::cache );
		manager.register( STORAGE_KEY, storage, CLEANABLE );
		this.manager = manager;
	}

	private Storage<Long, Brand> getStorage() {
		return manager.use( STORAGE_KEY );
	}

	@Override
	public ResultList<Brand> findAll() {
		Storage<Long, Brand> storage = getStorage();
		return new ResultListImpl<>( storage.values() );
	}

	@Override
	public ResultPage<Brand> findPage( int num, int size ) {
		Storage<Long, Brand> storage = getStorage();
		int offset = num * size, to = offset + size;
		List<Brand> b = storage.valuesBetween( offset, to );
		return new ResultPageImpl<>( b, num, storage.getRealSize(), size );
	}

	@Override
	public Result<Brand> findByKey( Long id ) {
		Storage<Long, Brand> storage = getStorage();
		if ( storage.isCaching( id ) ) {
			Optional<Brand> b = storage.get( id );
			return new ResultImpl<>( b.orElse( null ) );
		}
		Result<Brand> r = super.findByKey( id );
		storage.cache( id, r.isEmpty() ? null : r.get() );
		return r;
	}

	@Override
	public boolean exists( Long id ) {
		return getStorage().isCaching( id ) || super.exists( id );
	}

	@Override
	public Brand create( Brand brand ) {
		Brand created = super.create( brand );
		getStorage().cache( created );
		return created;
	}

	@Override
	public boolean update( Brand newVal, Brand oldVal ) {
		boolean isUpdated = super.update( newVal, oldVal );
		if ( isUpdated ) getStorage().cache( newVal );
		return isUpdated;
	}

	@Override
	public boolean remove( Brand brand ) {
		boolean isDeleted = super.remove( brand );
		if ( isDeleted ) getStorage().remove( brand );
		return isDeleted;
	}

	@Override
	public boolean remove( Long id ) {
		boolean isDeleted = super.remove( id );
		if ( isDeleted ) getStorage().remove( id );
		return isDeleted;
	}
}
