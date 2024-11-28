package se.pj.tbike.api.order.impl;

import com.ank.japi.HttpStatus;
import com.ank.japi.exception.HttpException;
import se.pj.tbike.api.order.data.OrderRepository;
import se.pj.tbike.api.order.data.OrderService;
import se.pj.tbike.api.order.entity.Order;
import se.pj.tbike.api.user.entity.User;
import se.pj.tbike.impl.SimpleCrudService;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl
        extends SimpleCrudService<Order, Long, OrderRepository>
        implements OrderService {
    public OrderServiceImpl(OrderRepository repository) {
        super(repository);
    }

    @Override
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
