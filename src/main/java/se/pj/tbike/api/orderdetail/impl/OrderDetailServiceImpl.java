package se.pj.tbike.api.orderdetail.impl;

import se.pj.tbike.api.orderdetail.data.OrderDetailRepository;
import se.pj.tbike.api.orderdetail.data.OrderDetailService;
import se.pj.tbike.api.orderdetail.entity.OrderDetail;
import se.pj.tbike.api.orderdetail.entity.OrderDetail.Id;
import se.pj.tbike.impl.SimpleCrudService;

public class OrderDetailServiceImpl
        extends SimpleCrudService<OrderDetail, Id, OrderDetailRepository>
        implements OrderDetailService {
    public OrderDetailServiceImpl(OrderDetailRepository repository) {
        super(repository);
    }
}
