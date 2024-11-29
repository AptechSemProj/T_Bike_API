package se.pj.tbike.api.order.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.order.dto.OrderRequest;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(UpdateOrderController.API_URL)
@RestController
@RequiredArgsConstructor
public class UpdateOrderController {

    public static final String API_URL = "/api/orders/{id}";

    private static final RequestHandler<OrderRequest, Long> HANDLER;

    static {
        HANDLER = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

}
