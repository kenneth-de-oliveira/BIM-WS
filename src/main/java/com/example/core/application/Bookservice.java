package com.example.core.application;

import com.example.core.model.BookEntity;
import com.example.infrastructure.repo.dto.BookRecord;

import java.util.List;

public interface Bookservice {

    List<BookRecord> findAll();

    BookRecord findByIsbn(String isbn);

    void saveOrUpdate(BookEntity bookEntity);

    void deleteByIsbn(String isbn);

}
