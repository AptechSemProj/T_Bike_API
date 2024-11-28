package se.pj.tbike.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import se.pj.tbike.caching.Cache;
import se.pj.tbike.common.entity.IdentifiedEntity;
import se.pj.tbike.service.CrudService;
import se.pj.tbike.caching.CacheManager;
import se.pj.tbike.util.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public abstract class SimpleCacheableService<
        E extends IdentifiedEntity<E, K> & Cacheable<E>,
        K extends Comparable<K>,
        R extends JpaRepository<E, K>
        >
        extends SimpleCrudService<E, K, R>
        implements CrudService<E, K> {

    private final AtomicLong totalElements;
    private final CacheManager<K> manager;
    private final Supplier<E> instanceSupplier;

    public SimpleCacheableService(
            R repository,
            Supplier<E> instanceSupplier
    ) {
        super(repository);
        this.manager = createCacheManager();
        long count = repository.count();
        this.totalElements = new AtomicLong(count);
        this.instanceSupplier = instanceSupplier;
    }

    protected abstract CacheManager<K> createCacheManager();

    public final long getTotalElements() {
        return totalElements.get();
    }

    public final CacheManager<K> getCacheManager() {
        return manager;
    }

    protected final E createEntityFromCache(Cache c) {
        return instanceSupplier.get().fromCacheObject(c.get());
    }

    public final boolean isFullCaching() {
        return manager.size() == getTotalElements();
    }

    @Override
    public List<E> findAll() {
        if (getTotalElements() == manager.size()) {
            return manager.stream()
                    .map(this::createEntityFromCache)
                    .toList();
        }
        List<E> arr = super.findAll();
        arr.forEach(e -> manager.cache(e.getId(), e.toCacheObject()));
        return arr;
    }

    protected final Page<E> getPageFromCache(Pageable pageable) {
        int num = pageable.getPageNumber();
        int size = pageable.getPageSize();
        if (num < 0) {
            throw new IllegalArgumentException();
        }
        if (size < 0) {
            throw new IllegalArgumentException();
        }
        long totalElements = this.getTotalElements();
        int cacheSize = this.manager.size();
        int offset = num * size, to = offset + size;
        if (offset >= cacheSize) {
            return null;
        }
        int length;
        if (to <= cacheSize) {
            length = size;
        } else if (cacheSize == totalElements) {
            length = cacheSize - offset;
        } else {
            return null;
        }
        List<E> content = this.manager
                .stream().parallel()
                .skip(offset)
                .limit(length)
                .map(this::createEntityFromCache)
                .toList();
        return new PageImpl<>(content, pageable, totalElements);
    }

    @Override
    public Page<E> findPage(Pageable pageable) {
        Page<E> page = getPageFromCache(pageable);
        if (page != null) {
            return page;
        }
        Page<E> arr = super.findPage(pageable);
        arr.forEach(e -> manager.cache(e.getId(), e.toCacheObject()));
        return arr;
    }

    @Override
    public Optional<E> findByKey(K id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        Cache cache = manager.get(id);
        if (cache.isPresent()) {
            return Optional.of(createEntityFromCache(cache));
        }
        if (cache.isEmpty()) {
            return Optional.empty();
        }
        Optional<E> r = super.findByKey(id);
        manager.cache(id, r.isEmpty() ? Map.of() : r.get().toCacheObject());
        return r;
    }

    @Override
    public boolean existsById(K id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        if (manager.isCaching(id)) {
            return true;
        }
        if (repository.existsById(id)) {
            manager.cache(id, null);
            return true;
        }
        return false;
    }

    @Override
    public E create(E e) {
        E created = super.create(e);
        manager.cache(created.getId(), created.toCacheObject());
        totalElements.incrementAndGet();
        return created;
    }

    @Override
    public boolean update(E newVal, E oldVal) {
        boolean isUpdated = super.update(newVal, oldVal);
        if (isUpdated) {
            manager.set(newVal.getId(), newVal.toCacheObject());
        }
        return isUpdated;
    }

    @Override
    public boolean delete(E e) {
        boolean isDeleted = super.delete(e);
        if (isDeleted) {
            manager.remove(e.getId());
            totalElements.decrementAndGet();
        }
        return isDeleted;
    }

    @Override
    public boolean deleteById(K id) {
        boolean isDeleted = super.deleteById(id);
        if (isDeleted) {
            manager.remove(id);
            totalElements.decrementAndGet();
        }
        return isDeleted;
    }
}
