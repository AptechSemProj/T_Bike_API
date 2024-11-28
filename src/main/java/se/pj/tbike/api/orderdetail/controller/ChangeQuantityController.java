package se.pj.tbike.api.orderdetail.controller;

import com.ank.japi.HttpStatus;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.order.data.OrderService;
import se.pj.tbike.api.order.entity.Order;
import se.pj.tbike.api.orderdetail.data.OrderDetailService;
import se.pj.tbike.api.orderdetail.dto.OrderDetailMapper;
import se.pj.tbike.api.orderdetail.dto.OrderDetailRequest;
import se.pj.tbike.api.orderdetail.entity.OrderDetail;
import se.pj.tbike.api.user.data.UserService;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(ChangeQuantityController.API_URL)
@RestController
@RequiredArgsConstructor
public class ChangeQuantityController {

    public static final String API_URL = "/api/orders/{orderId}/details";

    private final RequestHandler<OrderDetailRequest, Long> requestHandler;

    private final OrderService orderService;
    private final OrderDetailService service;
    private final UserService userService;

    private final OrderDetailMapper mapper;

    private final ModifyOrderHelper helper;

    @PutMapping({"", "/"})
    public Response<Long> changeQuantity(
            @PathVariable String orderId,
            @RequestBody OrderDetailRequest request
    ) {
        return requestHandler.handle(request, (res, req) -> {
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
