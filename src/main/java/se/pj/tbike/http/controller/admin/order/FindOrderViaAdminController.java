package se.pj.tbike.http.controller.admin.order;

import com.ank.japi.HttpStatus;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import com.ank.japi.impl.StdRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.service.UserService;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.impl.ResponseConfigurerImpl;

import java.util.function.Supplier;

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(FindOrderViaAdminController.API_URL)
@RestController
@RequiredArgsConstructor
public class FindOrderViaAdminController {

    public static final String API_URL = "/api/orders/{userId}";

    private static final
    RequestHandler<OrderRequest, OrderResponse> HANDLER;

    static {
        HANDLER = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

    private final FindOrderProcessor processor;
    private final OrderMapper mapper;
    private final UserService userService;

    @GetMapping({"", "/"})
    public Response find(@PathVariable String userId) {
        return HANDLER.handle(null, (res, req) -> {
            long uid;
            try {
                uid = NumberUtils.parseNumber(userId, Long.class);
            } catch (IllegalArgumentException e) {
                throw new HttpException(
                        HttpStatus.BAD_REQUEST,
                        "Cannot parse [" + userId + "] to integer number."
                );
            }
            Supplier<HttpException> ex = () -> new HttpException(
                    HttpStatus.NOT_FOUND,
                    "User with id [" + uid + "] not found"
            );
            User user = userService.findByKey(uid).orElseThrow(ex);
            return res.ok(mapper.map(processor.process(user)));
        });
    }
}
