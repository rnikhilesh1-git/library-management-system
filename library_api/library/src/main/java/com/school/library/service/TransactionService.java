package com.school.library.service;

import com.school.library.dto.IssueRequest;
import com.school.library.entity.Book;
import com.school.library.entity.BookTransaction;
import com.school.library.entity.Member;
import com.school.library.enums.MemberStatus;
import com.school.library.enums.TransactionStatus;
import com.school.library.exception.LibraryException;
import com.school.library.repository.BookRepository;
import com.school.library.repository.BookTransactionRepository;
import com.school.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BookTransactionRepository transactionRepository;

    public BookTransaction issueBook(IssueRequest request) {

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new LibraryException("Book not found"));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new LibraryException("Member not found"));

        if (Boolean.TRUE.equals(book.getDeleted())) {
            throw new LibraryException("Cannot issue deleted book");
        }

        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new LibraryException("Inactive member cannot issue book");
        }

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new LibraryException("Book is not available");
        }

        boolean alreadyIssued = transactionRepository.existsByBookIdAndMemberIdAndStatus(
                book.getId(),
                member.getId(),
                TransactionStatus.ISSUED
        );

        if (alreadyIssued) {
            throw new LibraryException("Member has already issued this book");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BookTransaction transaction = new BookTransaction();
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setIssuedAt(LocalDateTime.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setStatus(TransactionStatus.ISSUED);

        return transactionRepository.save(transaction);
    }

    public BookTransaction returnBook(Long transactionId) {

        BookTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new LibraryException("Transaction not found"));

        if (transaction.getStatus() == TransactionStatus.RETURNED) {
            throw new LibraryException("Book already returned");
        }

        Book book = transaction.getBook();

        transaction.setReturnedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.RETURNED);

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return transactionRepository.save(transaction);
    }

    public List<BookTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<BookTransaction> getByMember(Long memberId) {
        return transactionRepository.findByMemberId(memberId);
    }

    public List<BookTransaction> getByBook(Long bookId) {
        return transactionRepository.findByBookId(bookId);
    }
}
