package com.example.api.soap;

import com.example.api.soap.mapper.CategoryMapper;
import com.example.core.application.CategoryService;
import com.example.inventorymanagement.CategoryRequest;
import com.example.inventorymanagement.CategoryResponse;
import com.example.inventorymanagement.DeleteCategoryRequest;
import com.example.inventorymanagement.SearchCategoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@AllArgsConstructor
public class CategoryEndpoint {

    private final CategoryService service;

    private final CategoryMapper mapper;

    @PayloadRoot(namespace = "http://com.example/BIM-WS", localPart = "DeleteCategoryRequest")
    @ResponsePayload
    public void deleteById(@RequestPayload DeleteCategoryRequest request) {

        var id = request.getId();

        service.deleteById(id);

    }

    @PayloadRoot(namespace = "http://com.example/BIM-WS", localPart = "CategoryRequest")
    @ResponsePayload
    public void save(@RequestPayload CategoryRequest request) {

        var categoryEntity = mapper.toEntity(request);

        service.save(categoryEntity);

    }

    @PayloadRoot(namespace = "http://com.example/BIM-WS", localPart = "SearchCategoryRequest")
    @ResponsePayload
    public CategoryResponse findById(@RequestPayload SearchCategoryRequest request) {
        var category = request.getCategory();

        var categoryEntity = service.findById(category.getId());

        return mapper.toResponse(categoryEntity);
    }

    @PayloadRoot(namespace = "http://com.example/BIM-WS", localPart = "retrieveAllCategories")
    @ResponsePayload
    public CategoryResponse retrieveAllCategories() {

        var categories = service.findAll();

        return mapper.toResponse(categories);
    }

}
