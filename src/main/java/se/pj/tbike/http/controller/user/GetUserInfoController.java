package se.pj.tbike.http.controller.user;

import com.ank.japi.Response;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.user.UserInfo;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.GET_USER_INFO_PATH)
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RestController
public class GetUserInfoController extends BaseController {


    public GetUserInfoController(
            ResponseConfigurer configurer
    ) {
        super(configurer);
    }

    @GetMapping({"", "/"})
    public Response get(Authentication auth) {
        return tryCatch(() -> {
            User user = (User) auth.getPrincipal();
            return ok(new UserInfo(
                    user.getId(),
                    user.getName(),
                    user.getPhoneNumber(),
                    user.getAvatarImage()
            ));
        });
    }

}
