package se.pj.tbike.api.core.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;
import se.pj.tbike.api.io.RequestType;

import java.util.List;

@Getter
@AllArgsConstructor
@Validated
public class ProductCreation implements RequestType {

	@NotBlank
	private String sku;

	@NotBlank
	private String name;

	@Min( 1 )
	private long brandId;

	@Min( 1 )
	private long categoryId;

	@NotBlank
	private String imageUrl;

	@Min( 0 )
	private long price;

	@NotNull
	private List<AttributeCreation> colors;

	@NotNull
	private ProductSpecifications specifications;

}
