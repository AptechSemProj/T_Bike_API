package se.pj.tbike.core.api.product.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.api.brand.data.BrandService;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.api.category.data.CategoryService;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.api.attribute.data.AttributeRepository;
import se.pj.tbike.core.util.SimpleCacheableService;
import se.pj.tbike.caching.CacheControl;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.util.Output.Pagination;

import java.time.Duration;
import java.util.List;

public class ProductServiceImpl
        extends SimpleCacheableService<Product, Long, ProductRepository>
        implements ProductService {

    private static final int      POOL_SIZE     = 10;
    private static final Duration MAX_LIFE_TIME = Duration.ofMinutes( 10 );

    private final ProductRepository   repository = getRepository();
    private final AttributeRepository attributeRepository;
    private final BrandService        brandService;
    private final CategoryService     categoryService;

    public ProductServiceImpl(
            ProductRepository repository,
            AttributeRepository attributeRepository,
            BrandService brandService,
            CategoryService categoryService
    ) {
        super( repository, Product::new, Product::getId );
        this.attributeRepository = attributeRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @Override
    protected CacheManager<Long> createCacheManager() {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize( POOL_SIZE );
        scheduler.initialize();
        var controller = new CacheControl( scheduler, MAX_LIFE_TIME );
        return new CacheManager<>( controller );
    }

    @Override
    public void deleteByBrand(Brand brand) {
        List<Product> products = repository.findAllByBrand( brand );
        products.forEach(
                p -> attributeRepository.deleteAllByProductId( p.getId() ) );
        repository.deleteAll( products );
    }

    // TODO: cache result
    @Override
    public Pagination<Product> search(
            int page, int size,
            String name, Long maxPrice, Long minPrice,
            Long brand, Long category
    ) {
        CacheManager<Long> manager = getCacheManager();
        PageRequest request = PageRequest.of( page, size );
        repository.findAll();
        get_from_cache:
        {
            long offset = request.getOffset();
            int cacheSize = manager.size();
            if ( offset >= cacheSize ) {
                break get_from_cache;
            }
            // manager.size() < (offset + size)
            if ( !isFullCaching() ) {
                break get_from_cache;
            }
            List<Product> products = manager
                    .stream().parallel()
                    .map( super::createEntityFromCache )
                    .filter( p -> {
                        if ( name == null ) {
                            return true;
                        }
                        String productName = p.getName().toLowerCase();
                        String n = name.toLowerCase();
                        return productName.contains( n );
                    } )
                    .filter( p -> {
                        if ( brand == null ) {
                            return true;
                        }
                        return brand.equals( p.getBrand().getId() );
                    } )
                    .filter( p -> {
                        if ( category == null ) {
                            return true;
                        }
                        return category.equals( p.getCategory().getId() );
                    } )
                    .filter( p -> p
                            .getAttributes()
                            .parallelStream()
                            .map( Attribute::getPrice )
                            .anyMatch( price -> {
                                long min = minPrice != null ? minPrice : 0;
                                long max = maxPrice != null
                                           ? maxPrice
                                           : Long.MAX_VALUE;
                                return price >= min && price <= max;
                            } )
                    )
                    .toList();
            long totalElements = products.size();
            int totalPages = (int) Math.ceil( totalElements / (double) size );
            return Pagination.of(
                    products.stream().skip( offset ).limit( size ).toList(),
                    request.getPageNumber(), request.getPageSize(),
                    totalElements, totalPages
            );
        }
        Page<Product> paged = repository.search(
                name, brand, category, minPrice, maxPrice, request );
        paged.forEach(
                p -> getCacheManager().cache( p.getId(), p.toCacheObject() ) );
        return Pagination.of(
                paged.getContent(), paged.getNumber(), paged.getSize(),
                paged.getTotalElements(), paged.getTotalPages()
        );
    }

    @Override
    public Product findBySku(String sku) {
        return null;
    }
}
