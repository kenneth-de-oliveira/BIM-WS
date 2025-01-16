package com.example.api.soap.mapper;

import com.example.core.model.CategoryEntity;
import com.example.inventorymanagement.CategoryRequest;
import com.example.inventorymanagement.CategoryResponse;
import com.example.inventorymanagement.SearchCategory;
import com.example.shared.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(CategoryEntity categoryEntity) {
        return Optional.ofNullable(categoryEntity).map(entity -> {
            var categoryResponse = new CategoryResponse();
            categoryResponse.getCategory().add(toCategory(entity));
            return categoryResponse;
        }).orElse(null);
    }

    public CategoryResponse toResponse(List<CategoryEntity> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new BusinessException("No categories found");
        }
        var categoryResponse = new CategoryResponse();
        var list = categories.stream().map(this::toCategory).toList();
        categoryResponse.getCategory().addAll(list);
        return categoryResponse;
    }

    private SearchCategory toCategory(CategoryEntity categoryEntity) {
        return Optional.ofNullable(categoryEntity).map(entity -> {
            var category = new SearchCategory();
            category.setId(entity.getId());
            category.setName(entity.getName());
            category.setDescription(entity.getDescription());
            return category;
        }).orElse(null);
    }

    public CategoryEntity toEntity(CategoryRequest request) {
        return Optional.ofNullable(request.getCategory()).map(c -> {
            var entity = new CategoryEntity();
            entity.setName(c.getName());
            entity.setDescription(c.getDescription());
            return entity;
        }).orElse(null);
    }

}