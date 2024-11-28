package se.pj.tbike.api.order.controller;

import com.ank.japi.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.pj.tbike.api.order.dto.OrderRequest;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(UpdateOrderController.API_URL)
@RestController
@RequiredArgsConstructor
public class UpdateOrderController {

    public static final String API_URL = "/api/orders/{id}";

    private final RequestHandler<OrderRequest, Long> requestHandler;

}
