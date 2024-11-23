package se.pj.tbike.core.api.attribute.impl;

import se.pj.tbike.core.api.attribute.data.AttributeRepository;
import se.pj.tbike.core.api.attribute.data.AttributeService;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.core.util.SimpleCrudService;

public class AttributeServiceImpl
        extends SimpleCrudService<Attribute, Long, AttributeRepository>
        implements AttributeService {

    public AttributeServiceImpl(AttributeRepository repository) {
        super(repository);
    }

    @Override
    public void deleteAllByProduct(Product product) {
        repository.deleteAllByProductId(product.getId());
    }
}
