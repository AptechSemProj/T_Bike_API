package se.pj.tbike.http.model.cart;

import com.ank.japi.exception.HttpException;
import lombok.AllArgsConstructor;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.entity.OrderDetail;

@AllArgsConstructor
public class ItemRequest {

    private final Long product;

    private final Long price;

    private final Integer quantity;

    private final String action;

    public long getProduct() {
        if (product == null) {
            throw HttpException.badRequest(
                    "Product id is required."
            );
        }
        if (product < 1) {
            throw HttpException.badRequest(
                    "Product id is invalid."
            );
        }
        return product;
    }

    public int getQuantity() {
        if (quantity == null) {
            throw HttpException.badRequest(
                    "Quantity is required."
            );
        }
        if (quantity < 0) {
            throw HttpException.badRequest(
                    "Quantity must be positive."
            );
        }
        return quantity;
    }

    public long getPrice() {
        if (price == null) {
            throw HttpException.badRequest(
                    "Price is required."
            );
        }
        if (price < 0) {
            throw HttpException.badRequest(
                    "Price must be positive."
            );
        }
        return price;
    }

    public CartItemAction getAction() {
        try {
            String act = action.trim().toUpperCase();
            switch (act) {
                case "C" -> {
                    return CartItemAction.CREATE;
                }
                case "D" -> {
                    return CartItemAction.DELETE;
                }
                case "U" -> {
                    return CartItemAction.UPDATE;
                }
                case "N" -> {
                    return CartItemAction.NONE;
                }
                default -> {
                    return CartItemAction.valueOf(act);
                }
            }
        } catch (IllegalArgumentException e) {
            throw HttpException.badRequest(
                    "Action is invalid."
            );
        }
    }

    public OrderDetail toEntity(Order order) {
        int quantity = getQuantity();
        long price = getPrice();
        OrderDetail.Id id = new OrderDetail.Id();
        id.setOrderId(order.getId());
        id.setProductId(getProduct());
        OrderDetail od = new OrderDetail();
        od.setId(id);
        od.setPrice(price);
        od.setQuantity(quantity);
        od.setTotalAmount(quantity * price);
        od.setOrder(order);
        return od;
    }

}
