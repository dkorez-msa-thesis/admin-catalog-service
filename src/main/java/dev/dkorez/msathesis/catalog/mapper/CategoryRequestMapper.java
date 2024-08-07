package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.entity.CategoryDao;
import dev.dkorez.msathesis.catalog.model.CategoryRequestDto;

public class CategoryRequestMapper {
    public static CategoryDao toEntity(CategoryRequestDto category) {
        CategoryDao entity = new CategoryDao();
        entity.setName(category.getName());
        entity.setDescription(category.getDescription());

        return entity;
    }
}
