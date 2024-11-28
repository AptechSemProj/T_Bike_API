package se.pj.tbike.api.order.data;

import se.pj.tbike.api.order.entity.Order;
import se.pj.tbike.api.user.entity.User;
import se.pj.tbike.service.CrudService;

import java.util.Optional;

public interface OrderService
        extends CrudService<Order, Long> {

    Optional<Order> findByUser(User user);

}
