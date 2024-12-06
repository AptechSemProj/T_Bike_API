package se.pj.tbike.http.model.cart;

import com.ank.japi.exception.HttpException;
import se.pj.tbike.domain.entity.Order;
import se.pj.tbike.domain.entity.OrderDetail;
import se.pj.tbike.http.model.order.OrderDetailRequest;

public class ItemRequest extends OrderDetailRequest {

    private final String action;

    public ItemRequest(
            Long product,
            Long price,
            Integer quantity,
            String action
    ) {
        super(product, price, quantity);
        this.action = action;
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
