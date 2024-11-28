package se.pj.tbike.api.order.dto;

import org.springframework.stereotype.Service;
import se.pj.tbike.api.order.entity.Order;

@Service
public class OrderMapper {

    public Order map(OrderRequest request) {
        Order order = new Order();
        order.setId(request.getId());
        order.setTotalAmount(0L);
        order.setStatus(request.getStatus());
        return order;
    }

    public OrderResponse map(Order order) {
        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setTotalAmount(order.getTotalAmount());
//        resp.set
        return null;
    }
}
