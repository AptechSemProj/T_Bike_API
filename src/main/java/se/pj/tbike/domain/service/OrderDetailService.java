package se.pj.tbike.domain.service;

import org.springframework.stereotype.Service;
import se.pj.tbike.domain.repository.OrderDetailRepository;
import se.pj.tbike.domain.entity.OrderDetail;
import se.pj.tbike.domain.entity.OrderDetail.Id;
import se.pj.tbike.impl.SimpleCrudService;
import se.pj.tbike.common.service.CrudService;

@Service
public class OrderDetailService
        extends SimpleCrudService<OrderDetail, Id, OrderDetailRepository>
        implements CrudService<OrderDetail, Id> {

    public OrderDetailService(OrderDetailRepository repository) {
        super(repository);
    }

}
