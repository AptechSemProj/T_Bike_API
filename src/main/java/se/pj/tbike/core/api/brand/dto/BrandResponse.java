package se.pj.tbike.core.api.brand.dto;

import lombok.Builder;
import lombok.Getter;
import se.pj.tbike.io.ResponseType;

@Getter
@Builder(builderClassName = "Builder")
public class BrandResponse
		implements ResponseType {

	private long id;

	private String name;

	private String imageUrl;

	private String description;

}