package se.pj.tbike.core.api.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import se.pj.tbike.core.api.orderdetail.dto.OrderDetailRequest;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderRequest {

    private Long user;

    private List<OrderDetailRequest> details;

//	private String status;

}
