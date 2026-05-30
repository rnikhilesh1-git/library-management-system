package com.school.library.service;

import com.school.library.dto.BookRequest;
import com.school.library.entity.Book;
import com.school.library.exception.LibraryException;
import com.school.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book addBook(BookRequest request) {

        if (bookRepository.existsByIsbnAndDeletedFalse(request.getIsbn())) {
            throw new LibraryException("Book with same ISBN already exists");
        }

        if (request.getAvailableCopies() > request.getTotalCopies()) {
            throw new LibraryException("Available copies cannot be greater than total copies");
        }

        Book book = new Book();
        mapRequestToBook(request, book);

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookRequest request) {

        Book book = getBook(id);

        if (!book.getIsbn().equals(request.getIsbn()) &&
                bookRepository.existsByIsbnAndDeletedFalse(request.getIsbn())) {
            throw new LibraryException("Book with same ISBN already exists");
        }

        if (request.getAvailableCopies() > request.getTotalCopies()) {
            throw new LibraryException("Available copies cannot be greater than total copies");
        }

        mapRequestToBook(request, book);

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks(String search) {

        if (search == null || search.isBlank()) {
            return bookRepository.findByDeletedFalse();
        }

        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCaseOrCategoryContainingIgnoreCaseAndDeletedFalse(
                        search, search, search, search
                );
    }

    public Book getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Book not found"));

        if (Boolean.TRUE.equals(book.getDeleted())) {
            throw new LibraryException("Book already deleted");
        }

        return book;
    }

    public void deleteBook(Long id) {
        Book book = getBook(id);
        book.setDeleted(true);
        bookRepository.save(book);
    }

    private void mapRequestToBook(BookRequest request, Book book) {
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setCategory(request.getCategory());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getAvailableCopies());
        book.setShelfLocation(request.getShelfLocation());
    }
}
