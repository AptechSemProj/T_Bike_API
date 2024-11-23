package se.pj.tbike.core.api.order.dto;

import se.pj.tbike.core.api.order.entity.Order;

public interface OrderMapper {

    Order map(OrderRequest request);

}
