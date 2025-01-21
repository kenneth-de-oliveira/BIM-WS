package com.example.api.soap;

import com.example.api.soap.mapper.CategoryMapper;
import com.example.core.application.CategoryService;
import com.example.core.model.CategoryEntity;
import com.example.inventorymanagement.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryEndpointTest {

    @Mock
    private CategoryService service;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryEndpoint endpoint;

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
    void shouldDeleteCategoryById() {
        var request = new DeleteCategoryRequest();
        request.setId(1L);

        doNothing().when(service).deleteById(1L);

        endpoint.deleteById(request);

        verify(service, times(1)).deleteById(1L);
    }

    @Test
    void shouldSaveCategory() {
        var request = new CategoryRequest();
        var category = new SearchCategory();
        category.setId(1L);
        request.setCategory(category);
        var categoryEntity = new CategoryEntity();
        var response = new CategoryResponse();

        when(mapper.toEntity(request)).thenReturn(categoryEntity);
        when(service.save(categoryEntity)).thenReturn(categoryEntity);
        when(mapper.toResponse(categoryEntity)).thenReturn(response);

        var result = endpoint.save(request);

        assertEquals(response, result);

        verify(mapper, times(1)).toEntity(request);
        verify(service, times(1)).save(categoryEntity);
        verify(mapper, times(1)).toResponse(categoryEntity);
    }

    @Test
    void shouldFindCategoryById() {
        var request = new SearchCategoryRequest();
        var category = new SearchCategory();
        category.setId(1L);
        request.setCategory(category);

        var categoryEntity = new CategoryEntity();
        var response = new CategoryResponse();

        when(service.findById(1L)).thenReturn(categoryEntity);
        when(mapper.toResponse(categoryEntity)).thenReturn(response);

        var result = endpoint.findById(request);

        assertEquals(response, result);

        verify(service, times(1)).findById(1L);
        verify(mapper, times(1)).toResponse(categoryEntity);
    }

    @Test
    void shouldRetrieveAllCategories() {
        var categories = new ArrayList<CategoryEntity>();
        var response = new CategoryResponse();

        when(service.findAll()).thenReturn(categories);
        when(mapper.toResponse(categories)).thenReturn(response);

        var result = endpoint.retrieveAllCategories();

        assertEquals(response, result);

        verify(service, times(1)).findAll();
        verify(mapper, times(1)).toResponse(categories);
    }

}