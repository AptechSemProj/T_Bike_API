package se.pj.tbike.http.controller.user;

import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.domain.service.UserService;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.user.UpdateUserInfoRequest;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.UPDATE_USER_INFO_PATH)
@PreAuthorize("hasRole('USER')")
@RestController
public class UpdateUserInfoController extends BaseController {

    private final UserService userService;

    public UpdateUserInfoController(
            ResponseConfigurer configurer,
            UserService userService
    ) {
        super(configurer);
        this.userService = userService;
    }

    @PutMapping({"", "/"})
    public Response update(
            @RequestBody UpdateUserInfoRequest req,
            Authentication auth
    ) {
        return tryCatch(() -> {
            User user = (User) auth.getPrincipal();
            if (req.avatar() != null) {
                user.setAvatarImage(req.avatar());
            }
            if (req.name() != null) {
                user.setName(req.name());
            }
            if (req.phoneNumber() != null) {
                user.setPhoneNumber(req.phoneNumber());
            }
            if (userService.update(user)) {
                return noContent();
            }
            throw HttpException.internalServerError(
                    "Cannot update user info."
            );
        });
    }
}
