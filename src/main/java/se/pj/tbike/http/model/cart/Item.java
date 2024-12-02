package se.pj.tbike.http.model.cart;

import lombok.Getter;
import se.pj.tbike.domain.entity.OrderDetail;

@Getter
public final class Item {

    private final Long product;

    private final int quantity;

    private final long totalAmount;

    Item(OrderDetail od) {
        this.product = od.getId().getProductId();
        this.quantity = od.getQuantity();
        this.totalAmount = od.getTotalAmount();
    }
}
