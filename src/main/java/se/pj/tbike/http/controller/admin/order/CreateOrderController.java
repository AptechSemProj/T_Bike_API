package se.pj.tbike.http.controller.admin.order;

import com.ank.japi.HttpStatus;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import com.ank.japi.impl.StdRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.service.UserService;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.impl.ResponseConfigurerImpl;

import java.util.Optional;
import java.util.function.Supplier;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(CreateOrderController.API_URL)
@RestController
@RequiredArgsConstructor
public class CreateOrderController {

    public static final String API_URL = "/api/orders";

    private static final RequestHandler<OrderRequest, Long> HANDLER;

    static {
        HANDLER = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

    private final OrderService service;
    private final UserService userService;

    private final OrderMapper mapper;

    @PostMapping({"", "/"})
    public Response create(@RequestBody OrderRequest request) {
        return HANDLER.handle(request, (res, req) -> {
            User user = findUser(req.getUser());
            Optional<Order> opt = service.findByUser(user);
            Order order;
            if (opt.isPresent()) {
                order = opt.get();
            } else {
                order = mapper.map(req);
                if (!order.isCart()) {
                    throw new HttpException(
                            HttpStatus.BAD_REQUEST,
                            "New orders must have a cart status."
                    );
                }
                order.setUser(user);
                service.create(order);
            }
            return res.ok(order.getId());
        });
    }

    private User findUser(Long id) {
        if (id == null) {
            Authentication auth = SecurityContextHolder
                    .getContext()
                    .getAuthentication();
            return (User) auth.getPrincipal();
        } else {
            Supplier<HttpException> ex = () -> new HttpException(
                    HttpStatus.NOT_FOUND,
                    "User with id [" + id + "] not found"
            );
            return userService.findByKey(id).orElseThrow(ex);
        }
    }
}
