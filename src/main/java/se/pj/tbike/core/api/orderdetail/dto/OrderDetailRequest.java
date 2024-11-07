package se.pj.tbike.core.api.orderdetail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailRequest {

	private Long product;

	private Long price;

	private Integer quantity;

}
