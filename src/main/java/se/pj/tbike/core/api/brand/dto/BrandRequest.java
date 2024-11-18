package se.pj.tbike.core.api.brand.dto;

import com.ank.japi.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import se.pj.tbike.io.RequestType;

@Getter
@AllArgsConstructor
public class BrandRequest
        implements RequestType,
                   Request<Long> {

	private String name;

	private String imageUrl;

	private String description;

}
