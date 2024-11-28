package se.pj.tbike.api.orderdetail.controller;

import com.ank.japi.HttpStatus;
import com.ank.japi.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import se.pj.tbike.api.order.data.OrderService;
import se.pj.tbike.api.order.entity.Order;

@Service
@RequiredArgsConstructor
public class ModifyOrderHelper {

    private final OrderService orderService;

    public Order findOrder(String orderId) {
        long oid;
        try {
            oid = NumberUtils.parseNumber(orderId, Long.class);
        } catch (IllegalArgumentException e) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot parse [" + orderId + "] to integer number."
            );
        }
        Order order = orderService
                .findByKey(oid)
                .orElseThrow(() -> new HttpException(
                        HttpStatus.NOT_FOUND,
                        "Order with id [" + oid + "] not found."
                ));
        if (order.isCart()) {
            return order;
        }
        throw new HttpException(
                HttpStatus.BAD_REQUEST,
                "Cannot modify order with id [" + oid + "]."
        );
    }

}
