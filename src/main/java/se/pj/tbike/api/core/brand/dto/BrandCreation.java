package se.pj.tbike.api.core.brand.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import se.pj.tbike.api.io.RequestType;

@Getter
@AllArgsConstructor
public class BrandCreation implements RequestType {

	private String name;

	private String introduction;

}
