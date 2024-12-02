package se.pj.tbike.http.controller.admin.orderdetail;

import com.ank.japi.HttpStatus;
import com.ank.japi.RequestHandler;
import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import com.ank.japi.impl.StdRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.service.AttributeService;
import se.pj.tbike.domain.entity.Attribute;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.service.OrderDetailService;
import se.pj.tbike.domain.entity.OrderDetail;
import se.pj.tbike.impl.ResponseConfigurerImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(CreateDetailController.API_URL)
@RestController
@RequiredArgsConstructor
public class CreateDetailController {

    public static final String API_URL = "/api/orders/{orderId}/details";

    private static final
    RequestHandler<List<OrderDetailRequest>, List<Long>> HANDLER;

    static {
        HANDLER = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

    private final AttributeService attrService;
    private final OrderDetailService service;
    private final OrderService orderService;

    private final OrderDetailMapper mapper;

    private final ModifyOrderHelper helper;

    @PostMapping({"", "/"})
    public Response create(
            @PathVariable String orderId,
            @RequestBody List<OrderDetailRequest> details
    ) {
        return HANDLER.handle(details, (res, req) -> {
            Order order = helper.findOrder(orderId);
            List<Long> pIds = req.parallelStream()
                    .map(OrderDetailRequest::getProduct)
                    .toList();
            AtomicLong totalAmount = new AtomicLong();
            Map<Long, Attribute> attrs = attrService.findAllById(pIds);
            req.parallelStream().forEach(odr -> {
                OrderDetail od = mapper.map(odr);
                long pid = odr.getProduct();
                Attribute attr = attrs.get(pid);
                if (attr == null) {
                    throw new HttpException(
                            HttpStatus.NOT_FOUND,
                            "Product with id [" + pid + "] not found."
                    );
                }
                od.setId(new OrderDetail.Id(order.getId(), pid));
                od.setOrder(order);
                od.setProduct(attr);
                service.create(od);
                totalAmount.addAndGet(od.getTotalAmount());
            });
            order.setTotalAmount(totalAmount.get());
            orderService.update(order);
            return res.created();
        });
    }
}
