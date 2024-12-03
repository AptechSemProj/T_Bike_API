package se.pj.tbike.http.controller.admin.order;

import com.ank.japi.Response;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.service.UserService;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.order.OrderMapper;
import se.pj.tbike.http.model.order.CreateOrderRequest;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.CREATE_ORDER_PATH)
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class CreateOrderController extends BaseController {

    private final OrderService service;
    private final UserService userService;

    private final OrderMapper mapper;

    public CreateOrderController(
            ResponseConfigurer configurer,
            OrderService service,
            UserService userService,
            OrderMapper mapper
    ) {
        super(configurer);
        this.service = service;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping({"", "/"})
    public Response create(@RequestBody CreateOrderRequest req) {
        return tryCatch(() -> {
            Order order = null;
//            User user = findUser(req.getUser());
//            Optional<Order> opt = service.findCartByUser(user);
//            if (opt.isPresent()) {
//                order = opt.get();
//            } else {
//                order = mapper.map(req);
//                if (!order.isCart()) {
//                    throw HttpException.badRequest(
//                            "New orders must have a cart status."
//                    );
//                }
//                order.setUser(user);
//                service.create(order);
//            }
            return created(order.getId());
        });
    }

//    private User findUser(Authentication auth, Long id) {
//        if (id == null) {
//            return (User) auth.getPrincipal();
//        } else {
//            return userService
//                    .findByKey(id)
//                    .orElseThrow(() -> HttpException.notFound(
//                            "User with id [" + id + "] not found"
//                    ));
//        }
//    }
}
