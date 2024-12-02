package se.pj.tbike.http.controller.admin.order;

import lombok.Getter;
import lombok.Setter;
import se.pj.tbike.http.controller.admin.orderdetail.OrderDetailResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private long totalAmount;

    private List<OrderDetailResponse> details = new ArrayList<>();

}
