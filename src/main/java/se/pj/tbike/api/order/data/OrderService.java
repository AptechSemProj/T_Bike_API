package se.pj.tbike.api.order.data;

import com.ank.japi.HttpStatus;
import com.ank.japi.exception.HttpException;
import org.springframework.stereotype.Service;
import se.pj.tbike.api.order.entity.Order;
import se.pj.tbike.api.user.entity.User;
import se.pj.tbike.impl.SimpleCrudService;
import se.pj.tbike.service.CrudService;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService
        extends SimpleCrudService<Order, Long, OrderRepository>
        implements CrudService<Order, Long> {

    public OrderService(OrderRepository repository) {
        super(repository);
    }

    public Optional<Order> findByUser(User user) {
        List<Order> orders = repository
                .findAllByUser(user)
                .parallelStream()
                .filter(Order::isCart)
                .toList();
        return switch (orders.size()) {
            case 0 -> Optional.empty();
            case 1 -> Optional.of(orders.get(0));
            default -> throw new HttpException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "User has more than one shopping cart."
            );
        };
    }
}
