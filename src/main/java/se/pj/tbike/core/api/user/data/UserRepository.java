package se.pj.tbike.core.api.user.data;

import org.springframework.stereotype.Repository;
import se.pj.tbike.core.api.user.entity.User;
import se.pj.tbike.core.common.respository.SoftDeletionRepository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends SoftDeletionRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
