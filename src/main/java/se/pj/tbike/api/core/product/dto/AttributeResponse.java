package se.pj.tbike.api.core.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.api.io.ResponseType;

@AllArgsConstructor
@Getter
public class AttributeResponse implements ResponseType {

	private long id;

	private String name;

	private String imageUrl;

	private long price;

	private int quantity;

}
