package se.pj.tbike.http.controller.admin.orderdetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailResponse {

    private Long productId;

    private int quantity;

    private long totalAmount;

}
