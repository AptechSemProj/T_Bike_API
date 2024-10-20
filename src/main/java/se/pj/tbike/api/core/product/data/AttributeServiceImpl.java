package se.pj.tbike.api.core.product.data;

import se.pj.tbike.api.common.entity.IdentifiedEntity;
import se.pj.tbike.api.core.product.Product.Attribute;
import se.pj.tbike.service.StdCrudService;

public class AttributeServiceImpl
		extends StdCrudService<Attribute, Long>
		implements AttributeService {

	public AttributeServiceImpl( AttributeRepository repository) {
		super( repository, IdentifiedEntity::getId );
	}
}
