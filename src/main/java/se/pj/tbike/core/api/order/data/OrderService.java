package se.pj.tbike.core.api.order.data;

import se.pj.tbike.core.api.order.entity.Order;
import se.pj.tbike.service.CrudService;

public interface OrderService
        extends CrudService<Order, Long> {
}
