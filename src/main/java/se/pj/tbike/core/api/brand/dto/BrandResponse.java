package se.pj.tbike.core.api.brand.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {

    private long id;

    private String name;

    private String imageUrl;

    private String description;

}
