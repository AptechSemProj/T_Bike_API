package se.pj.tbike.core.util;

import java.util.List;
import java.util.Optional;

import com.ank.japi.annotation.Old;
import com.ank.japi.annotation.Unstable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import se.pj.tbike.core.common.entity.IdentifiedEntity;
import se.pj.tbike.service.CrudService;
import se.pj.tbike.service.NCrudService;
import se.pj.tbike.util.Output;

public class SimpleCrudService<
        E extends IdentifiedEntity<E, K>, K extends Comparable<K>,
        R extends JpaRepository<E, K>
        > implements CrudService<E, K>, NCrudService<E, K> {

    protected final R repository;

    public SimpleCrudService(R repository) {
        this.repository = repository;
    }

    @Override
    public Output.Array<E> findAll() {
        return findAll(Sort.unsorted());
    }

    @Override
    public Output.Array<E> findAll(Sort sort) {
        List<E> list = repository.findAll(sort);
        return Output.array(list);
    }

    @Override
    public Output.Pagination<E> findPage(int num, int size) {
        PageRequest req = PageRequest.of(num, size);
        Page<E> page = repository.findAll(req);
        return Output.pagination(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public Output.Value<E> findByKey(K id) {
        Optional<E> o = repository.findById(id);
        return Output.value(o.orElse(null));
    }

    @Override
    public boolean exists(E e) {
        return repository.exists(Example.of(e));
    }

    @Override
    public boolean existsByKey(K id) {
        return repository.existsById(id);
    }

    @Old
    @Override
    public E create(E e) {
        return repository.save(e);
    }

    @Old
    @Override
    public boolean update(E newVal, E oldVal) {
        if (oldVal != null) {
            if (!exists(oldVal)) {
                return false;
            }
            if (oldVal.equals(newVal)) {
                return true;
            }
        }
        repository.save(newVal);
        return true;
    }

    @Old
    @Override
    public boolean update(E e) {
        return update(e, null);
    }

    @Override
    public boolean removeByKey(K id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean remove(E e) {
        repository.delete(e);
        return true;
    }

    @Unstable
    @Override
    public E save(E e, Action action) {
        switch (action) {
            case INSERT, UPDATE, DYNAMIC -> repository.save(e);
        }
        return e;
    }
}
