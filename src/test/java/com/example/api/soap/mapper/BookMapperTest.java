package com.example.api.soap.mapper;

import com.example.shared.exceptions.BusinessException;
import com.example.infrastructure.repo.dto.BookRecord;
import com.example.inventorymanagement.Book;
import com.example.inventorymanagement.BookRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookMapperTest {

    @InjectMocks
    private BookMapper mapper;

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
    void shouldMapBookRecordToBookResponse() {
        var bookRecord = mock(BookRecord.class);

        when(bookRecord.isbn()).thenReturn("1234567890");

        assertDoesNotThrow(() -> {
            mapper.toResponse(bookRecord);
        });
    }

    @Test
    void shouldMapBookRecordListToBookResponse() {
        var bookRecord = mock(BookRecord.class);

        when(bookRecord.isbn()).thenReturn("1234567890");

        var bookRecords = List.of(bookRecord);

        assertDoesNotThrow(() -> {
            mapper.toResponse(bookRecords);
        });
    }

    @Test
    void shouldThrowBusinessExceptionWhenBookRecordListIsNull() {
        assertThrows(BusinessException.class, () -> {
            mapper.toResponse((List<BookRecord>) null);
        });
    }

    @Test
    void shouldThrowBusinessExceptionWhenBookRecordListIsEmpty() {
        assertThrows(BusinessException.class, () -> {
            mapper.toResponse(List.of());
        });
    }

    @Test
    void shouldMapBookRequestToBookEntity() {
        var request = new BookRequest();
        var book = new Book();
        book.setIsbn("1234567890");
        request.setBook(book);

        assertDoesNotThrow(() -> {
            mapper.toEntity(request);
        });
    }

}