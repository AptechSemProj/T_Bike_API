package se.pj.tbike.http.controller.banner;

import com.ank.japi.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.http.Routes;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.GET_BANNER_PATH)
@RestController
public class BannerController extends BaseController {
    public BannerController(ResponseConfigurer configurer) {
        super(configurer);
    }

    @GetMapping({"", "/"})
    public Response get() {
        return tryCatch(() -> {
            try (var input = BannerController.class.getResourceAsStream("/banner.json")) {
                ObjectMapper mapper = new ObjectMapper();
                return ok(mapper.readValue(input, Response.class));
            }
        });
    }

}
