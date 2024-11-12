package se.pj.tbike.core.api.attribute.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.ank.japi.Request;
import se.pj.tbike.core.api.attribute.entity.Attribute;
import se.pj.tbike.io.RequestType;

@Getter
@AllArgsConstructor
public class AttributeRequest
		implements RequestType,
		Request<Attribute> {

	@NotBlank
	private String name;

	private String imageUrl;

	@Min(0)
	private long price;

	@Min(0)
	private int quantity;

	private Boolean represent;

	@Override
	public Attribute toHandleableObject() {
		return null;
	}
}
