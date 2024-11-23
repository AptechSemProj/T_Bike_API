package se.pj.tbike.core.api.order.impl;

import com.ank.japi.HttpStatus;
import com.ank.japi.exception.HttpException;
import se.pj.tbike.core.api.order.dto.OrderMapper;
import se.pj.tbike.core.api.order.dto.OrderRequest;
import se.pj.tbike.core.api.order.entity.Order;
import se.pj.tbike.core.api.order.entity.Status;

public class OrderMapperImpl
        implements OrderMapper {

    @Override
    public Order map(OrderRequest request) {
        Order order = new Order();
        Status status;
        try {
            status = Status.valueOf(request.getStatus());
        } catch (IllegalArgumentException e) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "The status [" + request.getStatus() + "] is incorrect."
            );
        }
        order.setStatus(status);
        order.setTotalAmount(0L);
        return order;
    }
}
