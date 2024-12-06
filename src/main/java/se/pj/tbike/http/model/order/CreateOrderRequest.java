package se.pj.tbike.http.model.order;

import com.ank.japi.exception.HttpException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CreateOrderRequest {

    private final Long user;

    private final List<OrderDetailRequest> details;

    public long getUser() {
        if (user == null) {
            throw HttpException.badRequest(
                    "User id is required."
            );
        }
        if (user < 1) {
            throw HttpException.badRequest(
                    "Invalid user id."
            );
        }
        return user;
    }
}
