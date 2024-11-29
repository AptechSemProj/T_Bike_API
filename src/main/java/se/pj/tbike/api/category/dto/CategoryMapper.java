package se.pj.tbike.api.category.dto;

import org.springframework.stereotype.Service;
import se.pj.tbike.api.category.entity.Category;

@Service
public class CategoryMapper {

    public Category map(CategoryRequest req) {
        Category category = new Category();
        category.setId(req.getId());
        category.setName(req.getName());
        category.setImageUrl(req.getImageUrl());
        category.setDescription(req.getDescription());
        return category;
    }

    public CategoryResponse map(Category category) {
        CategoryResponse resp = new CategoryResponse();
        resp.setId(category.getId());
        resp.setName(category.getName());
        resp.setImageUrl(category.getImageUrl());
        resp.setDescription(category.getDescription());
        return resp;
    }
}
