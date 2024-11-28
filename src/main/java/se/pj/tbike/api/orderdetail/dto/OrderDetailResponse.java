package se.pj.tbike.api.orderdetail.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailResponse {

    private Long productId;

    private int quantity;

    private long totalAmount;

}
