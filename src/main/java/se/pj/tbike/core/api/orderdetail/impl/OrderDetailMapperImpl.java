package se.pj.tbike.core.api.orderdetail.impl;

import com.ank.japi.HttpStatus;
import com.ank.japi.exception.HttpException;
import se.pj.tbike.core.api.orderdetail.dto.OrderDetailMapper;
import se.pj.tbike.core.api.orderdetail.dto.OrderDetailRequest;
import se.pj.tbike.core.api.orderdetail.entity.OrderDetail;

public class OrderDetailMapperImpl implements OrderDetailMapper {
    @Override
    public OrderDetail map(OrderDetailRequest request) {
        Long productId = request.getProduct();
        if (productId == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Product id is required."
            );
        }
        Integer quantity = request.getQuantity();
        if (quantity == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity is required."
            );
        }
        Long price = request.getPrice();
        if (price == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Price is required."
            );
        }
        OrderDetail od = new OrderDetail();
        od.setQuantity(quantity);
        od.setTotalAmount(price * quantity);
        return od;
    }
}
