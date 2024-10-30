package se.pj.tbike.core.api.attribute.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.pj.tbike.core.api.attribute.entity.Attribute;

@Repository
public interface AttributeRepository
		extends JpaRepository<Attribute, Long> {

	void deleteAllByProductId(@Param("productId") Long productId);

}
