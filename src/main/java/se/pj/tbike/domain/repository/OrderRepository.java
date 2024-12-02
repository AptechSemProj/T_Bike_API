package se.pj.tbike.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.entity.User;

import java.util.List;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

}
