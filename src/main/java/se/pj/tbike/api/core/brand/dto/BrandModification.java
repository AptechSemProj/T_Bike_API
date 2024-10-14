package se.pj.tbike.api.core.brand.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.api.io.RequestType;

@Getter
@AllArgsConstructor
public class BrandModification implements RequestType {

	private long id;

	private String name;

	private String introduction;

	private String imageUrl;

}
