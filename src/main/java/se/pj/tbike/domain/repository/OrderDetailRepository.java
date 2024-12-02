package se.pj.tbike.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.pj.tbike.domain.entity.OrderDetail;
import se.pj.tbike.domain.entity.OrderDetail.Id;

public interface OrderDetailRepository
        extends JpaRepository<OrderDetail, Id> {
}
