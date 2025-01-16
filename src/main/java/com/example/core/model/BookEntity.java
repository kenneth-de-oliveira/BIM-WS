package com.example.core.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;

@Getter
@Setter
@Builder
@Entity(name = "book")
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String authorName;

    @NotNull
    private String text;

    @ISBN
    @NotNull
    private String isbn;

    @NotNull
    @Column(name = "category_entity_id")
    private Long categoryId;

}