package se.pj.tbike.api.core.brand.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.api.io.ResponseType;

@Getter
@AllArgsConstructor
public class BrandResponse implements ResponseType {

	private long id;

	private String name;

	private String introduction;

	private String imageUrl;

}
