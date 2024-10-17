package se.pj.tbike.api.core.product.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.api.io.RequestType;

@Getter
@AllArgsConstructor
public class CategoryModification implements RequestType {

	private long id;

	private String name;

	private String description;

}
