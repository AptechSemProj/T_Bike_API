package se.pj.tbike.http.controller.admin.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindOrderProcessor {

    private final OrderService service;

    @SuppressWarnings("DuplicatedCode")
    public Order process(User user) {
        Optional<Order> opt = service.findByUser(user);
        Order order;
        if (opt.isPresent()) {
            order = opt.get();
        } else {
            order = new Order();
            order.setStatus(Order.Status.CART);
            order.setTotalAmount(0L);
            order.setUser(user);
            service.create(order);
        }
        return order;
    }

}
