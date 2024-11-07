package se.pj.tbike.core.api.brand.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import se.pj.tbike.core.api.brand.entity.Brand;
import se.pj.tbike.core.common.respository.SoftDeletionRepository;

@EnableJpaRepositories
@Repository
public interface BrandRepository
		extends //JpaRepository<Brand, Long>,
		SoftDeletionRepository<Brand, Long> {
}
