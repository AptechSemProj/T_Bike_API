package se.pj.tbike.core.api.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping(CreateOrderController.API_URL)
@RestController
@RequiredArgsConstructor
public class CreateOrderController {

    public static final String API_URL = "/api/orders";

}
