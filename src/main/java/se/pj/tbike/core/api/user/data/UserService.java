package se.pj.tbike.core.api.user.data;

import se.pj.tbike.core.api.user.entity.User;
import se.pj.tbike.service.CrudService;

import java.util.Optional;

public interface UserService
        extends CrudService<User, Long> {

    Optional<User> findByUsername(String username);

}
