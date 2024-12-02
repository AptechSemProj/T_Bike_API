package se.pj.tbike.domain.repository;

import org.springframework.stereotype.Repository;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.common.respository.SoftDeletionRepository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends SoftDeletionRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
