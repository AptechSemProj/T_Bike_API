package se.pj.tbike.api.core.brand.data;

import se.pj.tbike.api.core.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository
		extends JpaRepository<Brand, Long> {
}
