package se.pj.tbike.http.model.order;

import org.springframework.stereotype.Service;
import se.pj.tbike.domain.entity.OrderDetail;

@Service
public class OrderDetailMapper {

    public OrderDetail map(OrderDetailRequest request) {
        int quantity = request.getQuantity();
        OrderDetail od = new OrderDetail();
        od.setProductId(request.getProduct());
        od.setQuantity(quantity);
        od.setTotalAmount(request.getPrice() * quantity);
        return od;
    }

    public OrderDetailResponse map(OrderDetail od) {
        OrderDetailResponse odr = new OrderDetailResponse();
        odr.setProductId(od.getId().getProductId());
        odr.setQuantity(od.getQuantity());
        odr.setTotalAmount(od.getTotalAmount());
        return odr;
    }

}
