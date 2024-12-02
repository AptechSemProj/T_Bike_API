package se.pj.tbike.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import se.pj.tbike.common.entity.IdentifiedEntity;
import se.pj.tbike.common.service.CrudService;

public class SimpleCrudService<
        E extends IdentifiedEntity<E, K>, K extends Comparable<K>,
        R extends JpaRepository<E, K>
        > implements CrudService<E, K> {

    protected final R repository;

    public SimpleCrudService(R repository) {
        this.repository = repository;
    }

    @Override
    public List<E> findAll() {
        return findAll(Sort.unsorted());
    }

    @Override
    public List<E> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<E> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<E> findByKey(K id) {
        return repository.findById(id);
    }

    @Override
    public boolean exists(E e) {
        return repository.exists(Example.of(e));
    }

    @Override
    public boolean existsById(K id) {
        return repository.existsById(id);
    }

    @Override
    public E create(E e) {
        return repository.save(e);
    }

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
        E updated = repository.save(newVal);
        return updated.equals(newVal);
    }

    @Override
    public boolean update(E e) {
        return update(e, null);
    }

    @Override
    public boolean deleteById(K id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean delete(E e) {
        repository.delete(e);
        return true;
    }
}
