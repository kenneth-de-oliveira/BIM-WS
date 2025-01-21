package com.example.api.soap.mapper;

import com.example.core.model.CategoryEntity;
import com.example.shared.exceptions.BusinessException;
import com.example.inventorymanagement.CategoryRequest;
import com.example.inventorymanagement.SearchCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryMapperTest {

    @InjectMocks
    private CategoryMapper mapper;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void shouldMapCategoryEntityToCategoryResponse() {
        var categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Category Name");

        assertDoesNotThrow(() -> {
            mapper.toResponse(categoryEntity);
        });
    }

    @Test
    void shouldMapCategoryEntityListToCategoryResponse() {
        var categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Category Name");
        var categoryEntities = List.of(categoryEntity);

        assertDoesNotThrow(() -> {
            mapper.toResponse(categoryEntities);
        });
    }

    @Test
    void shouldThrowBusinessExceptionWhenCategoryListIsEmpty() {
        assertThrows(BusinessException.class, () -> {
            mapper.toResponse(List.of());
        });
    }

    @Test
    void shouldThrowBusinessExceptionWhenCategoryListIsNull() {
        assertThrows(BusinessException.class, () -> {
            mapper.toResponse((List<CategoryEntity>) null);
        });
    }

    @Test
    void shouldMapCategoryRequestToCategoryEntity() {
        var request = new CategoryRequest();
        var category = new SearchCategory();
        category.setId(1L);
        request.setCategory(category);

        assertDoesNotThrow(() -> {
            mapper.toEntity(request);
        });
    }

}