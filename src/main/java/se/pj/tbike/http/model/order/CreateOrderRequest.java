package se.pj.tbike.http.model.order;

import com.ank.japi.exception.HttpException;
import lombok.AllArgsConstructor;
import org.springframework.util.NumberUtils;
import se.pj.tbike.domain.entity.Order;

@AllArgsConstructor
public class CreateOrderRequest {

    private String user;

    private String status;

    public Long getUser() {
        if (user == null) {
            throw HttpException.badRequest("User id is required.");
        }
        long userId;
        try {
            userId = NumberUtils.parseNumber(user, Long.class);
        } catch (IllegalArgumentException e) {
            throw HttpException.badRequest(
                    "Cannot parse [" + user + "] to integer number."
            );
        }
        if (userId < 1) {
            throw HttpException.badRequest(
                    "Invalid user id."
            );
        }
        return userId;
    }

    public Order.Status getStatus() {
        if (status == null) {
            return Order.Status.CART;
        }
        if (status.isBlank()) {
            throw HttpException.badRequest(
                    "Status is blank."
            );
        }
        try {
            return Order.Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw HttpException.badRequest(
                    "The status [" + status + "] is incorrect."
            );
        }
    }
}
