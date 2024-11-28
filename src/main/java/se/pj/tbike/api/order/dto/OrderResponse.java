package se.pj.tbike.api.order.dto;

import lombok.Getter;
import lombok.Setter;
import se.pj.tbike.api.orderdetail.dto.OrderDetailResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private long totalAmount;

    private List<OrderDetailResponse> details = new ArrayList<>();

}
