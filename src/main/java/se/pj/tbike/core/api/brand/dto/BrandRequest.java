package se.pj.tbike.core.api.brand.dto;

import com.ank.japi.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrandRequest implements Request<Long> {

    private Long id;

    private String name;

    private String imageUrl;

    private String description;

}
