package com.example.api.soap;

import com.example.api.soap.mapper.BookMapper;
import com.example.core.application.Bookservice;
import com.example.core.model.BookEntity;
import com.example.infrastructure.repo.dto.BookRecord;
import com.example.inventorymanagement.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookEndpointTest {

    @Mock
    private Bookservice service;

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookEndpoint endpoint;

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
    void shouldDeleteBookByIsbn() {
        var request = new DeleteBookRequest();
        request.setIsbn("1234567890");

        doNothing().when(service).deleteByIsbn(anyString());

        endpoint.deleteByIsbn(request);

        verify(service, times(1)).deleteByIsbn("1234567890");
    }

    @Test
    void shouldSaveOrUpdateBook() {
        var request = new BookRequest();
        var book = new Book();
        book.setIsbn("1234567890");
        request.setBook(book);

        var bookEntity = new BookEntity();

        when(mapper.toEntity(any(BookRequest.class))).thenReturn(bookEntity);
        doNothing().when(service).saveOrUpdate(any(BookEntity.class));

        endpoint.saveOrUpdate(request);

        verify(mapper, times(1)).toEntity(request);
        verify(service, times(1)).saveOrUpdate(bookEntity);
    }

    @Test
    void shouldFindBookByIsbn() {
        var request = new SearchBookRequest();
        var book = new SearchBook();
        book.setIsbn("1234567890");
        request.setBook(book);

        var bookRecord = mock(BookRecord.class);
        var bookResponse = new BookResponse();

        when(service.findByIsbn(anyString())).thenReturn(bookRecord);
        when(mapper.toResponse(any(BookRecord.class))).thenReturn(bookResponse);

        var response = endpoint.findByIsbn(request);

        assertNotNull(response);

        verify(service, times(1)).findByIsbn("1234567890");
        verify(mapper, times(1)).toResponse(bookRecord);
    }

    @Test
    void shouldRetrieveAllBooks() {
        var bookRecords = List.of(mock(BookRecord.class));
        var bookResponse = new BookResponse();

        when(service.findAll()).thenReturn(bookRecords);
        when(mapper.toResponse(anyList())).thenReturn(bookResponse);

        var response = endpoint.retrieveAllBooks();

        assertNotNull(response);

        verify(service, times(1)).findAll();
        verify(mapper, times(1)).toResponse(bookRecords);
    }

}