package se.pj.tbike.core.api.order.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.pj.tbike.core.api.order.entity.Order;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Long> {
}
