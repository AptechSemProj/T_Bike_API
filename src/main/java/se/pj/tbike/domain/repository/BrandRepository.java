package se.pj.tbike.domain.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import se.pj.tbike.domain.entity.Brand;
import se.pj.tbike.common.respository.SoftDeletionRepository;

@EnableJpaRepositories
@Repository
public interface BrandRepository
        extends SoftDeletionRepository<Brand, Long> {
}
