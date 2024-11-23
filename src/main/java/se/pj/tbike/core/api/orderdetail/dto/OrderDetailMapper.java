package se.pj.tbike.core.api.orderdetail.dto;

import se.pj.tbike.core.api.orderdetail.entity.OrderDetail;

public interface OrderDetailMapper {

    OrderDetail map(OrderDetailRequest request);

}
