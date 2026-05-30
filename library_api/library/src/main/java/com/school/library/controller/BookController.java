package com.school.library.controller;

import com.school.library.dto.BookRequest;
import com.school.library.entity.Book;
import com.school.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public Book createBook(@Valid @RequestBody BookRequest request) {
        return bookService.addBook(request);
    }

    @PutMapping("/{bookId}/update")
    public Book updateBook(@PathVariable Long bookId,
                           @Valid @RequestBody BookRequest request) {
        return bookService.updateBook(bookId, request);
    }

    @GetMapping("/list")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks(null);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.getAllBooks(keyword);
    }

    @GetMapping("/{bookId}/details")
    public Book getBookDetails(@PathVariable Long bookId) {
        return bookService.getBook(bookId);
    }

    @DeleteMapping("/{bookId}/soft-delete")
    public String softDeleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return "Book deleted successfully";
    }
}
