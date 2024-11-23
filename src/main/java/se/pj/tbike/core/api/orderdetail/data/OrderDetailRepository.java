package se.pj.tbike.core.api.orderdetail.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.pj.tbike.core.api.orderdetail.entity.OrderDetail;
import se.pj.tbike.core.api.orderdetail.entity.OrderDetail.Id;

public interface OrderDetailRepository
        extends JpaRepository<OrderDetail, Id> {
}
