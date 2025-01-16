package com.example.api.soap.mapper;

import com.example.core.model.BookEntity;
import com.example.infrastructure.repo.dto.BookRecord;
import com.example.inventorymanagement.*;
import com.example.shared.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookMapper {

    public BookResponse toResponse(BookRecord bookRecord) {
        return Optional.ofNullable(bookRecord).map(record -> {
            var bookResponse = new BookResponse();
            bookResponse.getBook().add(toBook(record));
            return bookResponse;
        }).orElse(null);
    }

    public BookResponse toResponse(List<BookRecord> recordList) {
        if (recordList == null || recordList.isEmpty()) {
            throw new BusinessException("No books found");
        }
        var bookResponse = new BookResponse();
        var books = recordList.stream().map(this::toBook).toList();
        bookResponse.getBook().addAll(books);
        return bookResponse;
    }

    public BookEntity toEntity(BookRequest bookRequest) {
        return Optional.ofNullable(bookRequest).map(request -> {
            var book = request.getBook();
            var bookEntity = new BookEntity();
            bookEntity.setTitle(book.getTitle());
            bookEntity.setIsbn(book.getIsbn());
            bookEntity.setAuthorName(book.getAuthorName());
            bookEntity.setText(book.getText());
            bookEntity.setCategoryId(book.getCategoryId());
            return bookEntity;
        }).orElse(null);
    }

    private SearchBook toBook(BookRecord bookRecord) {
        return Optional.ofNullable(bookRecord).map(entity -> {
            var book = new SearchBook();
            book.setId(bookRecord.id());
            book.setTitle(bookRecord.title());
            book.setIsbn(bookRecord.isbn());
            book.setAuthorName(bookRecord.authorName());
            book.setText(bookRecord.text());
            book.setCategory(toCategory(bookRecord.id(), bookRecord.categoryName(), bookRecord.categoryDescription()));
            return book;
        }).orElse(null);
    }

    private SearchCategory toCategory(Long id, String categoryName, String categoryDescription) {
        var category = new SearchCategory();
        category.setId(id);
        category.setName(categoryName);
        category.setDescription(categoryDescription);
        return category;
    }

}