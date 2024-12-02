package se.pj.tbike.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.pj.tbike.domain.entity.Attribute;

@Repository
public interface AttributeRepository
		extends JpaRepository<Attribute, Long> {

	void deleteAllByProductId(@Param("productId") Long productId);

}
