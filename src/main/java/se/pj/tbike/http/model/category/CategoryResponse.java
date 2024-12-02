package se.pj.tbike.http.model.category;

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
