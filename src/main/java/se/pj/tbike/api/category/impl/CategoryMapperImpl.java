package se.pj.tbike.api.category.impl;

import se.pj.tbike.api.category.dto.CategoryMapper;
import se.pj.tbike.api.category.dto.CategoryRequest;
import se.pj.tbike.api.category.dto.CategoryResponse;
import se.pj.tbike.api.category.entity.Category;

public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category map(CategoryRequest req) {
        Category category = new Category();
        category.setId(req.getId());
        category.setName(req.getName());
        category.setImageUrl(req.getImageUrl());
        category.setDescription(req.getDescription());
        return category;
    }

    @Override
    public CategoryResponse map(Category category) {
        CategoryResponse resp = new CategoryResponse();
        resp.setId(category.getId());
        resp.setName(category.getName());
        resp.setImageUrl(category.getImageUrl());
        resp.setDescription(category.getDescription());
        return resp;
    }
}
