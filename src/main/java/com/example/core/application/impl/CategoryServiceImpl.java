package com.example.core.application.impl;

import com.example.core.application.CategoryService;
import com.example.core.model.CategoryEntity;
import com.example.infrastructure.repo.CategoryRepository;
import com.example.shared.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public CategoryEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Category not found"));
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        return repository.save(categoryEntity);
    }

    @Override
    public void deleteById(Long id) {

        repository.findById(id).ifPresentOrElse( repository::delete, () -> {
            throw new BusinessException("Category not found");
        });
    }

}