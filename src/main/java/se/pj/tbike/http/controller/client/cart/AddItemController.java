package se.pj.tbike.http.controller.client.cart;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.http.Routes;
import se.pj.tbike.impl.BaseController;

@RequestMapping(Routes.ADD_CART_ITEM_PATH)
@PreAuthorize("hasRole('USER')")
@RestController
public class AddItemController extends BaseController {

    public AddItemController(
            ResponseConfigurer configurer
    ) {
        super(configurer);
    }

    @PutMapping({"", "/"})
    public void add() {

    }
}
