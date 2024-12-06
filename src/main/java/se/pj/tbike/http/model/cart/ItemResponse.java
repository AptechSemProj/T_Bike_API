package se.pj.tbike.http.model.cart;

import lombok.Getter;
import se.pj.tbike.domain.entity.Attribute;
import se.pj.tbike.domain.entity.OrderDetail;

@Getter
public final class ItemResponse {

    private final Long product;

    private final String name;

    private final String imageUrl;

    private final String color;

    private final int totalQuantity;

    private final int quantity;

    private final long price;

    private final long totalAmount;

    private final CartItemAction action;

    ItemResponse(OrderDetail od) {
        Attribute attr = od.getProduct();
        this.product = attr.getId();
        this.name = attr.getProduct().getName();
        this.imageUrl = attr.getImageUrl();
        this.color = attr.getColor();
        this.totalQuantity = attr.getQuantity();
        this.quantity = od.getQuantity();
        this.totalAmount = od.getTotalAmount();
        this.price = od.getPrice();
        this.action = CartItemAction.NONE;
    }
}
