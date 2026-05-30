package com.school.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "books", indexes = {
        @Index(name = "idx_book_isbn", columnList = "isbn"),
        @Index(name = "idx_book_title", columnList = "title")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @Column(unique = true)
    private String isbn;

    private String category;

    private Integer totalCopies;

    private Integer availableCopies;

    private String shelfLocation;

    private Boolean deleted = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (deleted == null) {
            deleted = false;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
