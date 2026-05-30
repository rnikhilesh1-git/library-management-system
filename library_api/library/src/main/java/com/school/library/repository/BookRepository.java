package com.school.library.repository;

import com.school.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbnAndDeletedFalse(String isbn);

    List<Book> findByDeletedFalse();

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCaseOrCategoryContainingIgnoreCaseAndDeletedFalse(
            String title,
            String author,
            String isbn,
            String category
    );
}
