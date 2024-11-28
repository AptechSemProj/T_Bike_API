package se.pj.tbike.api.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private long id;

    private String name;

    private String imageUrl;

    private String description;

}
