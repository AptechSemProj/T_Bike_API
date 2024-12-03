package se.pj.tbike.domain.service;

import com.ank.japi.exception.HttpException;
import org.springframework.stereotype.Service;
import se.pj.tbike.domain.repository.OrderRepository;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.impl.SimpleCrudService;
import se.pj.tbike.common.service.CrudService;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService
        extends SimpleCrudService<Order, Long, OrderRepository>
        implements CrudService<Order, Long> {

    public OrderService(OrderRepository repository) {
        super(repository);
    }

    public Optional<Order> findCartByUser(User user) {
        List<Order> orders = repository
                .findAllByUser(user)
                .parallelStream()
                .filter(Order::isCart)
                .toList();
        return switch (orders.size()) {
            case 0 -> Optional.empty();
            case 1 -> Optional.of(orders.get(0));
            default -> throw HttpException.internalServerError(
                    "User has more than one shopping cart."
            );
        };
    }
}
