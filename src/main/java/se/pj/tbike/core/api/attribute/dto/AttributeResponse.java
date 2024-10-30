package se.pj.tbike.core.api.attribute.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.io.ResponseType;

@AllArgsConstructor
@Getter
public class AttributeResponse implements ResponseType {

	private long id;

	private String name;

	private String imageUrl;

	private long price;

	private int quantity;

}
