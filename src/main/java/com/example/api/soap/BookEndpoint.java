package com.example.api.soap;

import com.example.api.soap.mapper.BookMapper;
import com.example.core.application.Bookservice;
import com.example.inventorymanagement.BookRequest;
import com.example.inventorymanagement.BookResponse;
import com.example.inventorymanagement.DeleteBookRequest;
import com.example.inventorymanagement.SearchBookRequest;
import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@AllArgsConstructor
public class BookEndpoint {

    private final Bookservice service;

    private final BookMapper mapper;

    @PayloadRoot(namespace = "http://com.example/BIM-WS", localPart = "DeleteBookRequest")
    @ResponsePayload
    public void deleteByIsbn(@RequestPayload DeleteBookRequest request) {

        var isbn = request.getIsbn();

        service.deleteByIsbn(isbn);

    }

    @PayloadRoot(namespace = "http://com.example/BIM-WS", localPart = "BookRequest")
    @ResponsePayload
    public void saveOrUpdate(@RequestPayload BookRequest request) {

        var bookEntity = mapper.toEntity(request);

        service.saveOrUpdate(bookEntity);

    }

    @PayloadRoot(namespace = "http://com.example/BIM-WS", localPart = "SearchBookRequest")
    @ResponsePayload
    public BookResponse findByIsbn(@RequestPayload SearchBookRequest request) {
        var isbn = request.getBook().getIsbn();

        var book = service.findByIsbn(isbn);

        return mapper.toResponse(book);
    }

    @PayloadRoot(namespace = "http://com.example/BIM-WS", localPart = "retrieveAllBooks")
    @ResponsePayload
    public BookResponse retrieveAllBooks() {

        var books = service.findAll();

        return mapper.toResponse(books);
    }

}