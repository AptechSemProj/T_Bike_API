package se.pj.tbike.core.api.category.dto;

import se.pj.tbike.core.api.category.entity.Category;

public interface CategoryMapper {

    Category map(CategoryRequest req);

    CategoryResponse map(Category category);

}
