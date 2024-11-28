package se.pj.tbike.api.order.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.pj.tbike.api.order.entity.Order;
import se.pj.tbike.api.user.entity.User;

import java.util.List;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

}
