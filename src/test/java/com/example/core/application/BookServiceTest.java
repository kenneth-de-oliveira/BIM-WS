package com.example.core.application;

import com.example.core.application.impl.BookServiceImpl;
import com.example.core.model.BookEntity;
import com.example.core.model.CategoryEntity;
import com.example.infrastructure.repo.BookRepository;
import com.example.infrastructure.repo.CategoryRepository;
import com.example.infrastructure.repo.dto.BookRecord;
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

class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

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
    void shouldReturnAllBooks() {
        var bookRecords = List.of(mock(BookRecord.class));

        when(bookRepository.searchAllBooks()).thenReturn(bookRecords);

        var result = bookService.findAll();

        assertEquals(bookRecords, result);

        verify(bookRepository, times(1)).searchAllBooks();
    }

    @Test
    void shouldReturnBookByIsbn() {
        var isbn = "1234567890";
        var bookRecord = mock(BookRecord.class);

        when(bookRepository.searchByIsbn(isbn)).thenReturn(Optional.of(bookRecord));

        var result = bookService.findByIsbn(isbn);

        assertEquals(bookRecord, result);

        verify(bookRepository, times(1)).searchByIsbn(isbn);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFoundByIsbn() {
        var isbn = "1234567890";

        when(bookRepository.searchByIsbn(isbn)).thenReturn(Optional.empty());

        var exception = assertThrows(BusinessException.class, () -> bookService.findByIsbn(isbn));

        assertEquals("Book not found", exception.getMessage());

        verify(bookRepository, times(1)).searchByIsbn(isbn);
    }

    @Test
    void shouldSaveNewBook() {
        var categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Category");
        categoryEntity.setDescription("Description");

        var bookEntity = new BookEntity();
        bookEntity.setCategoryId(1L);
        bookEntity.setIsbn("1234567890");

        when(categoryRepository.findById(bookEntity.getCategoryId())).thenReturn(Optional.of(categoryEntity));
        when(bookRepository.findByIsbn(bookEntity.getIsbn())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> bookService.saveOrUpdate(bookEntity));

        verify(bookRepository, times(1)).save(bookEntity);
    }

    @Test
    void shouldUpdateExistingBook() {
        var categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Category");
        categoryEntity.setDescription("Description");

        var existingBookEntity = new BookEntity();
        existingBookEntity.setCategoryId(1L);
        existingBookEntity.setIsbn("1234567890");

        var bookEntity = new BookEntity();
        bookEntity.setCategoryId(1L);
        bookEntity.setIsbn("1234567890");
        bookEntity.setAuthorName("Author");
        bookEntity.setTitle("Title");
        bookEntity.setText("Text");

        when(categoryRepository.findById(bookEntity.getCategoryId())).thenReturn(Optional.of(categoryEntity));
        when(bookRepository.findByIsbn(bookEntity.getIsbn())).thenReturn(Optional.of(existingBookEntity));

        assertDoesNotThrow(() -> bookService.saveOrUpdate(bookEntity));

        verify(bookRepository, times(1)).save(existingBookEntity);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        var bookEntity = new BookEntity();
        bookEntity.setCategoryId(1L);

        when(categoryRepository.findById(bookEntity.getCategoryId())).thenReturn(Optional.empty());

        var exception = assertThrows(BusinessException.class, () -> bookService.saveOrUpdate(bookEntity));

        assertEquals("Category not found", exception.getMessage());

        verify(categoryRepository, times(1)).findById(bookEntity.getCategoryId());
        verify(bookRepository, never()).save(any(BookEntity.class));
    }

    @Test
    void shouldDeleteBookByIsbn() {
        var isbn = "1234567890";
        var bookEntity = new BookEntity();
        bookEntity.setIsbn(isbn);

        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(bookEntity));

        assertDoesNotThrow(() -> bookService.deleteByIsbn(isbn));

        verify(bookRepository, times(1)).deleteByIsbn(isbn);
    }

    @Test
    void shouldThrowExceptionWhenDeletingBookNotFoundByIsbn() {
        var isbn = "1234567890";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        var exception = assertThrows(BusinessException.class, () -> bookService.deleteByIsbn(isbn));

        assertEquals("Book not found", exception.getMessage());

        verify(bookRepository, times(1)).findByIsbn(isbn);
        verify(bookRepository, never()).deleteByIsbn(isbn);
    }

}