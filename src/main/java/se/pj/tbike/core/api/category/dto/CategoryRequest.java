package se.pj.tbike.core.api.category.dto;

import com.ank.japi.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryRequest implements Request<Long> {

    private Long id;

    private String name;

    private String imageUrl;

    private String description;

}
