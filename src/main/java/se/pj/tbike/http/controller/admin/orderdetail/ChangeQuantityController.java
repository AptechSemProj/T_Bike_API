package se.pj.tbike.http.controller.admin.orderdetail;

import com.ank.japi.HttpStatus;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import com.ank.japi.impl.StdRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.service.OrderDetailService;
import se.pj.tbike.domain.entity.OrderDetail;
import se.pj.tbike.domain.service.UserService;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(ChangeQuantityController.API_URL)
@RestController
@RequiredArgsConstructor
public class ChangeQuantityController {

    public static final String API_URL = "/api/orders/{orderId}/details";

    private static final
    RequestHandler<OrderDetailRequest, Long> HANDLER;

    static {
        HANDLER = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

    private final OrderService orderService;
    private final OrderDetailService service;
    private final UserService userService;

    private final OrderDetailMapper mapper;

    private final ModifyOrderHelper helper;

    @PutMapping({"", "/"})
    public Response changeQuantity(
            @PathVariable String orderId,
            @RequestBody OrderDetailRequest request
    ) {
        return HANDLER.handle(request, (res, req) -> {
            Order order = helper.findOrder(orderId);
            long pid = req.getProduct();
            OrderDetail.Id id = new OrderDetail.Id(
                    order.getId(), pid
            );
            OrderDetail od = order.getDetails().get(id);
            if (od == null) {
                throw new HttpException(
                        HttpStatus.NOT_FOUND,
                        "Product with id [" + pid + "] not exists in order."
                );
            }
            od.setQuantity(od.getQuantity());
            od.setTotalAmount(request.getTotalAmount());
            return null;
        });
    }

}
