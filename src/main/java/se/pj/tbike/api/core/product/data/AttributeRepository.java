package se.pj.tbike.api.core.product.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.pj.tbike.api.core.product.Product.Attribute;

public interface AttributeRepository
		extends JpaRepository<Attribute, Long> {
}
