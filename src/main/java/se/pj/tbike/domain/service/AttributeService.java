package se.pj.tbike.domain.service;

import org.springframework.stereotype.Service;
import se.pj.tbike.domain.repository.AttributeRepository;
import se.pj.tbike.domain.entity.Attribute;
import se.pj.tbike.domain.entity.Product;
import se.pj.tbike.impl.SimpleCrudService;
import se.pj.tbike.common.service.CrudService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AttributeService
        extends SimpleCrudService<Attribute, Long, AttributeRepository>
        implements CrudService<Attribute, Long> {

    public AttributeService(AttributeRepository repository) {
        super(repository);
    }

    public void deleteAllByProduct(Product product) {
        repository.deleteAllByProductId(product.getId());
    }

    public Map<Long, Attribute> findAllById(List<Long> ids) {
        return repository.findAllById(ids)
                .parallelStream()
                .collect(Collectors.toMap(
                        Attribute::getId,
                        Function.identity()
                ));
    }
}
