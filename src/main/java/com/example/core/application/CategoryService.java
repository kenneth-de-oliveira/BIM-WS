package com.example.core.application;

import com.example.core.model.CategoryEntity;

import java.util.List;

public interface CategoryService {

    List<CategoryEntity> findAll();

    CategoryEntity findById(Long id);

    void save(CategoryEntity categoryEntity);

    void deleteById(Long id);

}
