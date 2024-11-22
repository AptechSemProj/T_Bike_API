package se.pj.tbike.core.api.product.impl;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.StringUtils;
import se.pj.tbike.core.api.attribute.data.AttributeService;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.api.product.data.ProductRepository;
import se.pj.tbike.core.api.product.data.ProductService;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.util.SimpleCacheableService;
import se.pj.tbike.caching.CacheControl;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.util.Output.Pagination;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ProductServiceImpl
        extends SimpleCacheableService<Product, Long, ProductRepository>
        implements ProductService {

    private static final int POOL_SIZE = 10;
    private static final Duration MAX_LIFE_TIME = Duration.ofMinutes(10);

    private final AttributeService attributeService;

    public ProductServiceImpl(
            ProductRepository repository,
            AttributeService attributeService
    ) {
        super(repository, Product::new, Product::getId);
        this.attributeService = attributeService;
    }

    @Override
    protected CacheManager<Long> createCacheManager() {
        var scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.initialize();
        var controller = new CacheControl(scheduler, MAX_LIFE_TIME);
        return new CacheManager<>(controller);
    }

    private List<Product> searchInCache(long offset, CacheManager<Long> manager,
                                        String name, Long brand, Long category,
                                        Range<Long> range) {
        if (offset >= manager.size()) {
            return null;
        }
        // manager.size() < (offset + size)
        if (!isFullCaching()) {
            return null;
        }
        Stream<Product> stream = manager
                .stream().parallel()
                .map(super::createEntityFromCache);
        if (brand != null && brand > 0) {
            stream = stream.filter(p -> brand.equals(p.getBrand().getId()));
        }
        if (category != null && category > 0) {
            stream = stream.filter(
                    p -> category.equals(p.getCategory().getId())
            );
        }
        if (StringUtils.hasText(name)) {
            stream = stream.filter(p -> {
                String n1 = p.getName().toLowerCase();
                String n2 = name.toLowerCase();
                return n1.contains(n2);
            });
        }
        stream = stream.filter(p -> p.getAttributes()
                .parallelStream()
                .map(Attribute::getPrice)
                .anyMatch(range::contains)
        );
        return stream.toList();
    }

    private Specification<Product> containsName(String name) {
        return (root, query, builder) -> {
            if (StringUtils.hasText(name)) {
                return builder.like(
                        builder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
            }
            return builder.conjunction();
        };
    }

    private Specification<Product> hasBrand(Long id) {
        return (root, query, builder) -> {
            root.join("brand").on(builder.equal(
                    root.get("brand").get("deleted"), false
            ));
            if (id != null && id > 0) {
                return builder.equal(
                        root.get("brand").get("id"), id
                );
            }
            return builder.conjunction();
        };
    }

    private Specification<Product> hasCategory(Long id) {
        return (root, query, builder) -> {
            root.join("category").on(builder.equal(
                    root.get("category").get("deleted"), false
            ));
            if (id != null && id > 0) {
                return builder.equal(
                        root.get("category").get("id"), id
                );
            }
            return builder.conjunction();
        };
    }

    private Specification<Product> hasPriceInRange(Range<Long> range) {
        return (root, query, builder) -> {
            if (query != null) {
                query.distinct(true);
            }
            Optional<Long> min = range.getLowerBound().getValue();
            Optional<Long> max = range.getUpperBound().getValue();
            return builder.between(
                    root.join("attributes").get("price"),
                    min.orElse(0L), max.orElse(Long.MAX_VALUE)
            );
        };
    }

    @Override
    public Pagination<Product> search(Pageable pageable, String name,
                                      Long brand, Long category,
                                      Range<Long> range) {
        Page<Product> paged;
        CacheManager<Long> manager = getCacheManager();
        long offset = pageable.getOffset();
        List<Product> products = searchInCache(
                offset, manager, name, brand, category, range
        );
        if (products != null) {
            paged = new PageImpl<>(
                    products.stream()
                            .skip(offset)
                            .limit(pageable.getPageSize())
                            .toList(),
                    pageable,
                    products.size()
            );
        } else {
            paged = getRepository().findAll(Specification.allOf(
                    hasBrand(brand),
                    hasCategory(category),
                    containsName(name),
                    hasPriceInRange(range)
            ), pageable);
            paged.forEach(p -> manager
                    .cache(p.getId(), p.toCacheObject())
            );
        }
        return Pagination.of(paged.getContent(),
                paged.getNumber(), paged.getSize(),
                paged.getTotalElements(), paged.getTotalPages()
        );
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return getRepository().findBySku(sku);
    }

    @Override
    public boolean remove(Product product) {
        attributeService.deleteAllByProduct(product);
        return super.remove(product);
    }

    @Override
    public boolean removeByKey(Long id) {
        Optional<Product> o = getRepository().findById(id);
        return o.filter(this::remove).isPresent();
    }
}
