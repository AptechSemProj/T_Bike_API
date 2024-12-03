package se.pj.tbike.http.model.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.http.controller.admin.orderdetail.OrderDetailMapper;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderDetailMapper detailMapper;

    public Order map(CreateOrderRequest request) {
        Order order = new Order();
        order.setTotalAmount(0L);
        order.setStatus(request.getStatus());
        return order;
    }

    public OrderResponse map(Order order) {
        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setTotalAmount(order.getTotalAmount());
        resp.setDetails(
                order.getDetails().values()
                        .parallelStream()
                        .map(detailMapper::map)
                        .toList()
        );
        return resp;
    }
}
