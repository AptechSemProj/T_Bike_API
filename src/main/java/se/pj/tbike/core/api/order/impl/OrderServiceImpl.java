package se.pj.tbike.core.api.order.impl;

import se.pj.tbike.core.api.order.data.OrderRepository;
import se.pj.tbike.core.api.order.data.OrderService;
import se.pj.tbike.core.api.order.entity.Order;
import se.pj.tbike.core.util.SimpleCrudService;

public class OrderServiceImpl
        extends SimpleCrudService<Order, Long, OrderRepository>
        implements OrderService {
    public OrderServiceImpl(OrderRepository repository) {
        super(repository);
    }
}
