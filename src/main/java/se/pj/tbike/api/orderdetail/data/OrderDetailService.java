package se.pj.tbike.api.orderdetail.data;

import se.pj.tbike.api.orderdetail.entity.OrderDetail;
import se.pj.tbike.service.CrudService;

public interface OrderDetailService
        extends CrudService<OrderDetail, OrderDetail.Id> {
}
