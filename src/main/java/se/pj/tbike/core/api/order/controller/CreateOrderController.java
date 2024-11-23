package se.pj.tbike.core.api.order.controller;

import com.ank.japi.HttpStatus;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.core.api.attribute.data.AttributeService;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.core.api.order.data.OrderService;
import se.pj.tbike.core.api.order.dto.OrderMapper;
import se.pj.tbike.core.api.order.dto.OrderRequest;
import se.pj.tbike.core.api.order.entity.Order;
import se.pj.tbike.core.api.orderdetail.data.OrderDetailService;
import se.pj.tbike.core.api.orderdetail.dto.OrderDetailMapper;
import se.pj.tbike.core.api.orderdetail.dto.OrderDetailRequest;
import se.pj.tbike.core.api.orderdetail.entity.OrderDetail;
import se.pj.tbike.core.api.user.data.UserService;
import se.pj.tbike.core.api.user.entity.User;
import se.pj.tbike.util.Output.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(CreateOrderController.API_URL)
@RestController
@RequiredArgsConstructor
public class CreateOrderController {

    public static final String API_URL = "/api/orders";

    private final RequestHandler<OrderRequest, Long> requestHandler;

    private final AttributeService attributeService;
    private final OrderService service;
    private final OrderDetailService detailService;
    private final UserService userService;

    private final OrderMapper mapper;
    private final OrderDetailMapper detailMapper;
    private final OrderService orderService;

    @PostMapping({"", "/"})
    public Response<Long> create(Authentication auth,
                                 @RequestBody OrderRequest request) {
        return requestHandler.handle(request, (res, req) -> {
            User user;
            Long userId = request.getUser();
            if (userId == null) {
                user = (User) auth.getPrincipal();
            } else {
                Value<User> o = userService.findByKey(userId);
                if (o.isNull()) {
                    throw new HttpException(
                            HttpStatus.NOT_FOUND,
                            "User with id [" + userId + "] not found"
                    );
                }
                user = o.get();
            }
            Order order = mapper.map(request);
            order.setUser(user);
            Order created = service.create(order);
            AtomicLong totalAmount = new AtomicLong();
            List<OrderDetailRequest> detailRequests = request.getDetails();
            List<OrderDetail> details = new ArrayList<>(detailRequests.size());
            detailRequests.parallelStream()
                    .forEach(detail -> {
                        Long productId = detail.getProduct();
                        Value<Attribute> attr = attributeService.findByKey(productId);
                        if (attr.isNull()) {
                            throw new HttpException(
                                    HttpStatus.NOT_FOUND,
                                    "Product with id [" + productId + "] not found."
                            );
                        }
                        OrderDetail.Id id = new OrderDetail.Id();
                        id.setOrderId(created.getId());
                        id.setProductId(productId);
                        OrderDetail detailDetail = detailMapper.map(detail);
                        detailDetail.setId(id);
                        detailDetail.setOrder(created);
                        detailDetail.setProduct(attr.get());
                        totalAmount.addAndGet(
                                detailService.create(detailDetail)
                                        .getTotalAmount()
                        );
                    });
            order.setDetails(details);
            order.setTotalAmount(totalAmount.get());
            if (orderService.update(order)) {
                return res.created(created.getId());
            }
            throw new HttpException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Create order failed."
            );
        });
    }
}
