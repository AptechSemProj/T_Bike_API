package se.pj.tbike.core.api.attribute.data;

import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.api.product.entity.Product;
import se.pj.tbike.service.CrudService;

public interface AttributeService
        extends CrudService<Attribute, Long> {

    void deleteAllByProduct(Product product);

}
