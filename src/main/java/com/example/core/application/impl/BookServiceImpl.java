package com.example.core.application.impl;

import com.example.core.application.Bookservice;
import com.example.core.model.BookEntity;
import com.example.infrastructure.repo.BookRepository;
import com.example.infrastructure.repo.CategoryRepository;
import com.example.infrastructure.repo.dto.BookRecord;
import com.example.shared.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements Bookservice {

    private final BookRepository repository;
    private final CategoryRepository categoryRepository;

    public List<BookRecord> findAll() {
        return repository.searchAllBooks();
    }

    @Override
    public BookRecord findByIsbn(String isbn) {
        return repository.searchByIsbn(isbn)
                .orElseThrow( () -> new BusinessException("Book not found"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BusinessException.class)
    public void saveOrUpdate(BookEntity bookEntity) {

        categoryRepository.findById(bookEntity.getCategoryId()).ifPresentOrElse(
                category -> {
                    repository.findByIsbn(bookEntity.getIsbn()).ifPresentOrElse(
                            entity -> {
                                entity.setAuthorName(bookEntity.getAuthorName());
                                entity.setIsbn(bookEntity.getIsbn());
                                entity.setTitle(bookEntity.getTitle());
                                entity.setText(bookEntity.getText());
                                entity.setCategoryId(bookEntity.getCategoryId());
                                repository.save(entity);
                            },
                            () -> repository.save(bookEntity)
                    );
                },
                () -> {
                    throw new BusinessException("Category not found");
                }
        );

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BusinessException.class)
    public void deleteByIsbn(String isbn) {
        repository.findByIsbn(isbn).ifPresentOrElse(
                entity -> repository.deleteByIsbn(entity.getIsbn()),
                () -> {
                    throw new BusinessException("Book not found");
                }
        );
    }

}