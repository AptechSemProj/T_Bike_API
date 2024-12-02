package se.pj.tbike.http.controller.admin.order;

import com.ank.japi.HttpStatus;
import com.ank.japi.Request;
import com.ank.japi.exception.HttpException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import se.pj.tbike.domain.entity.Order;

@Getter
@AllArgsConstructor
public class OrderRequest implements Request<Long> {

    private Long id;

    private Long user;

    private String status;

    public Long getUser() {
        if (user != null && user < 1) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid user id."
            );
        }
        return user;
    }

    public Order.Status getStatus() {
        if (status == null) {
            return Order.Status.CART;
        }
        if (status.isBlank()) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Status is blank."
            );
        }
        try {
            return Order.Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "The status [" + status + "] is incorrect."
            );
        }
    }
}
