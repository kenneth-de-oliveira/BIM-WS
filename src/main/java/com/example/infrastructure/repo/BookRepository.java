package com.example.infrastructure.repo;

import com.example.core.model.BookEntity;
import com.example.infrastructure.repo.dto.BookRecord;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query(value = "select b.id, b.isbn, b.title, b.text, b.author_name, c.id, c.name, c.description from book b join category c on b.category_entity_id = c.id WHERE b.isbn = :isbn", nativeQuery = true)
    Optional<BookRecord> searchByIsbn(@Param("isbn") @ISBN @NotNull String isbn);

    @Query(value = "select b.id, b.isbn, b.title, b.text, b.author_name, c.id, c.name, c.description from book b join category c on b.category_entity_id = c.id", nativeQuery = true)
    List<BookRecord> searchAllBooks();

    Optional<BookEntity> findByIsbn(@ISBN @NotNull String isbn);

    Optional<BookEntity> deleteByIsbn(@ISBN @NotNull String isbn);

}