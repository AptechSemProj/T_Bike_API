package se.pj.tbike.api.core.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttributeCreation {

	private String name;

	private String imageUrl;

	private long price;

	private int quantity;

}
