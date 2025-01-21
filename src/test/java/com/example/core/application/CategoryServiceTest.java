package com.example.core.application;

import com.example.core.application.impl.CategoryServiceImpl;
import com.example.core.model.CategoryEntity;
import com.example.infrastructure.repo.CategoryRepository;
import com.example.shared.exceptions.BusinessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

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
    void shouldReturnAllCategories() {
        var categoryEntities = List.of(mock(CategoryEntity.class));

        when(categoryRepository.findAll()).thenReturn(categoryEntities);

        var result = categoryService.findAll();

        assertEquals(categoryEntities, result);

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnCategoryById() {
        var id = 1L;
        var categoryEntity = mock(CategoryEntity.class);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(categoryEntity));

        var result = categoryService.findById(id);

        assertEquals(categoryEntity, result);

        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFoundById() {
        var id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(BusinessException.class, () -> categoryService.findById(id));

        assertEquals("Category not found", exception.getMessage());

        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void shouldSaveCategory() {
        var categoryEntity = new CategoryEntity();

        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

        var result = categoryService.save(categoryEntity);

        assertEquals(categoryEntity, result);

        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    void shouldDeleteCategoryById() {
        var id = 1L;
        var categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(categoryEntity));

        assertDoesNotThrow(() -> categoryService.deleteById(id));

        verify(categoryRepository, times(1)).delete(categoryEntity);
    }

    @Test
    void shouldThrowExceptionWhenDeletingCategoryNotFoundById() {
        var id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(BusinessException.class, () -> categoryService.deleteById(id));

        assertEquals("Category not found", exception.getMessage());

        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, never()).delete(any(CategoryEntity.class));
    }

}