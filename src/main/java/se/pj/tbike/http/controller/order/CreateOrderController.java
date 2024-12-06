package se.pj.tbike.http.controller.order;

import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.OrderDetail;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.domain.service.AttributeService;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.service.UserService;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.order.OrderDetailMapper;
import se.pj.tbike.http.model.order.OrderMapper;
import se.pj.tbike.http.model.order.CreateOrderRequest;
import se.pj.tbike.impl.BaseController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(Routes.CREATE_ORDER_PATH)
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class CreateOrderController extends BaseController {

    private final OrderService service;
    private final AttributeService attributeService;
    private final UserService userService;

    private final OrderMapper mapper;
    private final OrderDetailMapper detailMapper;

    public CreateOrderController(
            ResponseConfigurer configurer,
            OrderService service,
            AttributeService attributeService,
            UserService userService,
            OrderMapper mapper,
            OrderDetailMapper detailMapper
    ) {
        super(configurer);
        this.service = service;
        this.attributeService = attributeService;
        this.userService = userService;
        this.mapper = mapper;
        this.detailMapper = detailMapper;
    }

    @PostMapping({"", "/"})
    public Response create(@RequestBody CreateOrderRequest req) {
        return tryCatch(() -> {
            long uid = req.getUser();
            User user = userService
                    .findByKey(uid)
                    .orElseThrow(() -> HttpException.notFound(
                            "User with id [" + uid + "] not found."
                    ));
            List<OrderDetail> details = new ArrayList<>();
            List<Long> productIds = new ArrayList<>();
            req.getDetails().forEach(odr -> {
                OrderDetail od = detailMapper.map(odr);
                attributeService.findByKey(od.getProductId());
            });
            Order order = new Order();
            order.setUser(user);
            service.create(order);
            return created(order.getId());
        });
    }

//    private User findUser(Authentication auth, Long id) {
//        if (id == null) {
//            return (User) auth.getPrincipal();
//        } else {
//            return userService
//                    .findByKey(id)

//        }
//    }
}
