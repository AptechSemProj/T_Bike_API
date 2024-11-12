package se.pj.tbike.core.api.user.data;

import org.springframework.security.core.userdetails.UserDetailsService;
import se.pj.tbike.core.api.user.entity.User;
import se.pj.tbike.service.CrudService;

public interface UserService
        extends CrudService<User, Long>,
                UserDetailsService {
}
