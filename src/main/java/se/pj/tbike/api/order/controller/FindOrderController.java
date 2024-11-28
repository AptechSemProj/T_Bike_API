package se.pj.tbike.api.order.controller;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.order.data.OrderService;
import se.pj.tbike.api.order.dto.OrderMapper;
import se.pj.tbike.api.order.dto.OrderRequest;
import se.pj.tbike.api.order.dto.OrderResponse;
import se.pj.tbike.api.user.data.UserService;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(FindOrderController.API_URL)
@RestController
@RequiredArgsConstructor
public class FindOrderController {

    public static final String API_URL = "/api/orders";

    private static final
    RequestHandler<OrderRequest, OrderResponse> HANDLER;

    static {
        HANDLER = new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }

    private final OrderService service;
    private final UserService userService;

    private final OrderMapper mapper;

}
