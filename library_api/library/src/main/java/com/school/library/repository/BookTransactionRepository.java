package com.school.library.repository;

import com.school.library.entity.BookTransaction;
import com.school.library.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {

    boolean existsByBookIdAndMemberIdAndStatus(Long bookId, Long memberId, TransactionStatus status);

    List<BookTransaction> findByMemberId(Long memberId);

    List<BookTransaction> findByBookId(Long bookId);
}
