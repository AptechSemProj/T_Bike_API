package se.pj.tbike.http.helper;

import com.ank.japi.exception.HttpException;
import org.springframework.stereotype.Service;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.entity.User;

@Service
public record FindOrderProcessor(OrderService service) {

    public Order process(User user) {
        return service
                .findCartByUser(user)
                .orElseThrow(() -> HttpException.notFound(""));
    }

}
