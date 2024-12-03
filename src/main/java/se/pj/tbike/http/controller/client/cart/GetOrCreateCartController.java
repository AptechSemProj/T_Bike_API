package se.pj.tbike.http.controller.client.cart;

import com.ank.japi.Response;
import com.ank.japi.exception.HttpException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.entity.User;
import se.pj.tbike.domain.service.OrderService;
import se.pj.tbike.http.Routes;
import se.pj.tbike.http.model.cart.Cart;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.GET_CART_PATH)
@PreAuthorize("hasRole('USER')")
@RestController
public class GetOrCreateCartController extends BaseController {

    private final OrderService service;

    public GetOrCreateCartController(
            ResponseConfigurer configurer,
            OrderService service
    ) {
        super(configurer);
        this.service = service;
    }

    @GetMapping({"", "/"})
    public Response get(Authentication auth) {
        return tryCatch(() -> {
            if (auth == null) {
                throw HttpException.unauthorized();
            }
            User user = (User) auth.getPrincipal();
            Order order = service
                    .findCartByUser(user)
                    .orElseGet(() -> {
                        Order o = new Order();
                        o.setStatus(Order.Status.CART);
                        o.setTotalAmount(0L);
                        o.setUser(user);
                        return service.create(o);
                    });
            return ok(new Cart(order));
        });
    }
}
