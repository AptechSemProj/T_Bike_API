package se.pj.tbike.http.model.order;

import com.ank.japi.exception.HttpException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderDetailRequest {

    private final Long product;

    private final Long price;

    private final Integer quantity;

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

}
