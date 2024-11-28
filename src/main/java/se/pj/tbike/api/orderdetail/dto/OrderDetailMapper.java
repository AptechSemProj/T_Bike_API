package se.pj.tbike.api.orderdetail.dto;

import org.springframework.stereotype.Service;
import se.pj.tbike.api.orderdetail.entity.OrderDetail;

@Service
public class OrderDetailMapper {

    public OrderDetail map(OrderDetailRequest request) {
        int quantity = request.getQuantity();
        OrderDetail od = new OrderDetail();
        od.setQuantity(quantity);
        od.setTotalAmount(request.getPrice() * quantity);
        return od;
    }

}
