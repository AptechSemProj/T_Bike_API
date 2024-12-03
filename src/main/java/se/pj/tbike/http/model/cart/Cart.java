package se.pj.tbike.http.model.cart;

import com.ank.japi.exception.HttpException;
import lombok.Getter;
import se.pj.tbike.domain.entity.Order;

import java.util.List;

@Getter
public final class Cart {

    private final Long id;

    private final long totalAmount;

    private final List<ItemResponse> items;

    public Cart(final Order order) {
        if (!order.isCart()) {
            throw HttpException.internalServerError(
                    "Order is not a cart."
            );
        }
        this.id = order.getId();
        this.totalAmount = order.getTotalAmount();
        this.items = order.getDetails().values()
                .parallelStream()
                .map(ItemResponse::new)
                .toList();
    }
}
