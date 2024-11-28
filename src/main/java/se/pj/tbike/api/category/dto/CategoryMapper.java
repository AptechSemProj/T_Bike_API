package se.pj.tbike.api.category.dto;

import se.pj.tbike.api.category.entity.Category;

public interface CategoryMapper {

    Category map(CategoryRequest req);

    CategoryResponse map(Category category);

}
