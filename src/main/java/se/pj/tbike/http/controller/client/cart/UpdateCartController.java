package se.pj.tbike.http.controller.client.cart;

import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.Attribute;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.entity.OrderDetail;
import se.pj.tbike.domain.entity.OrderDetail.Id;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.domain.service.AttributeService;
import se.pj.tbike.domain.service.OrderDetailService;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.cart.ItemRequest;
import se.pj.tbike.impl.BaseController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RequestMapping(Routes.ADD_CART_ITEM_PATH)
@PreAuthorize("hasRole('USER')")
@RestController
public class UpdateCartController extends BaseController {

    private final OrderService service;
    private final OrderDetailService detailService;
    private final AttributeService attributeService;
    private final OrderService orderService;

    public UpdateCartController(
            ResponseConfigurer configurer,
            OrderService service,
            OrderDetailService detailService,
            AttributeService attributeService,
            OrderService orderService) {
        super(configurer);
        this.service = service;
        this.detailService = detailService;
        this.attributeService = attributeService;
        this.orderService = orderService;
    }

    @PutMapping({"", "/"})
    public Response update(
            Authentication auth,
            @RequestBody List<ItemRequest> items
    ) {
        return tryCatch(() -> {
            User user = (User) auth.getPrincipal();
            Order order = service
                    .findCartByUser(user)
                    .orElseThrow(() -> HttpException.notFound(
                            "Cart not found."
                    ));
            Map<Id, OrderDetail> details = order.getDetails();
            AtomicLong totalAmount = new AtomicLong(0L);
            items.parallelStream().forEach(item -> {
                OrderDetail od = item.toEntity(order);
                Id id = od.getId();
                long pid = id.getProductId();
                Attribute attr = attributeService
                        .findByKey(pid)
                        .orElseThrow(() -> HttpException.notFound(
                                "Product with id [" + pid
                                        + "] not found."
                        ));
                od.setProduct(attr);
                switch (item.getAction()) {
                    case CREATE -> {
                        if (details.containsKey(id)) {
                            throw HttpException.badRequest(
                                    "Product with id [" + pid
                                            + "] already exists in the cart."
                            );
                        }
                        detailService.create(od);
                        totalAmount.addAndGet(od.getTotalAmount());
                        attr.setQuantity(attr.getQuantity() - od.getQuantity());
                        attributeService.update(attr);
                    }
                    case UPDATE -> {
                        OrderDetail old = details.get(id);
                        if (old == null) {
                            throw HttpException.badRequest(
                                    "Product with id [" + pid
                                            + "] does not exists in the cart."
                            );
                        }
                        int q = old.getQuantity() - od.getQuantity();
                        old.setPrice(od.getPrice());
                        old.setQuantity(od.getQuantity());
                        old.setTotalAmount(od.getTotalAmount());
                        detailService.update(old);
                        totalAmount.addAndGet(od.getTotalAmount());
                        attr.setQuantity(attr.getQuantity() + q);
                        attributeService.update(attr);
                    }
                    case DELETE -> {
                        if (!details.containsKey(id)) {
                            throw HttpException.badRequest(
                                    "Product with id [" + pid
                                            + "] does not exists in the cart."
                            );
                        }
                        detailService.deleteById(id);
                        totalAmount.addAndGet(-od.getTotalAmount());
                        attr.setQuantity(attr.getQuantity() + od.getQuantity());
                        attributeService.update(attr);
                    }
                    default -> {
                    }
                }
            });
            order.setTotalAmount(totalAmount.get());
            orderService.update(order);
            return noContent();
        });
    }
}
