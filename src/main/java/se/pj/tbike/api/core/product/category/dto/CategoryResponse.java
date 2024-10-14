package se.pj.tbike.api.core.product.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.api.io.ResponseType;

@Getter
@AllArgsConstructor
public class CategoryResponse implements ResponseType {

	private long id;

	private String name;

	private String description;

}
