package se.pj.tbike.core.api.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;
import se.pj.tbike.core.api.attribute.dto.AttributeRequest;
import se.pj.tbike.io.RequestType;

import java.util.List;

@Getter
@AllArgsConstructor
@Validated
public class ProductRequest implements RequestType {

	private String sku;

	@NotBlank
	@Size( max = 255 )
	private String name;

	@Min( 1 )
	private long brandId;

	@Min( 1 )
	private long categoryId;

	@NotNull
	@Size( min = 1 )
	private List<AttributeRequest> colors;

	@NotNull
	private ProductSpecifications specifications;

}
