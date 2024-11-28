package se.pj.tbike.api.orderdetail.dto;

import com.ank.japi.HttpStatus;
import com.ank.japi.Request;
import com.ank.japi.exception.HttpException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailRequest implements Request<Long> {

    private Long product;

    private Long price;

    private Integer quantity;

    public long getTotalAmount() {
        return getQuantity() * getPrice();
    }

    public long getProduct() {
        if (product == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Product id is required."
            );
        }
        return product;
    }

    public long getPrice() {
        if (price == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Price is required."
            );
        }
        return price;
    }

    public int getQuantity() {
        if (quantity == null) {
            throw new HttpException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity is required."
            );
        }
        return quantity;
    }
}
