package se.pj.tbike.http.controller.order;

import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.order.OrderMapper;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.FIND_ORDER_PATH)
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class FindOrderController extends BaseController {

    private final OrderService orderService;
    private final OrderMapper mapper;

    public FindOrderController(
            ResponseConfigurer configurer,
            OrderService orderService,
            OrderMapper mapper
    ) {
        super(configurer);
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @GetMapping({"", "/"})
    public Response find(@PathVariable String id) {
        return tryCatch(() -> {
            long oid;
            try {
                oid = NumberUtils.parseNumber(id, Long.class);
            } catch (IllegalArgumentException e) {
                throw HttpException.badRequest(
                        "Cannot parse [" + id + "] to integer number."
                );
            }
            Order order = orderService
                    .findByKey(oid)
                    .orElseThrow(() -> HttpException.notFound(
                            "Order with id [" + oid + "] not found."
                    ));
            return ok(mapper.map(order));
        });
    }
}
