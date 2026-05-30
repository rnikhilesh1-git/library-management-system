package com.school.library.controller;

import com.school.library.dto.IssueRequest;
import com.school.library.entity.BookTransaction;
import com.school.library.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/library-transactions")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LibraryTransactionController {

    private final TransactionService transactionService;

    @PostMapping("/issue-book")
    public BookTransaction issueBook(@Valid @RequestBody IssueRequest request) {
        return transactionService.issueBook(request);
    }

    @PostMapping("/{transactionId}/return-book")
    public BookTransaction returnBook(@PathVariable Long transactionId) {
        return transactionService.returnBook(transactionId);
    }

    @GetMapping("/history")
    public List<BookTransaction> getTransactionHistory() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/member/{memberId}/history")
    public List<BookTransaction> getMemberTransactionHistory(@PathVariable Long memberId) {
        return transactionService.getByMember(memberId);
    }

    @GetMapping("/book/{bookId}/history")
    public List<BookTransaction> getBookTransactionHistory(@PathVariable Long bookId) {
        return transactionService.getByBook(bookId);
    }
}
