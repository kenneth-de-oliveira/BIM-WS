package com.example.infrastructure.repo.dto;

public record BookRecord(Long id, String isbn, String title, String text, String authorName, Long categoryId, String categoryName,
                         String categoryDescription) {

}