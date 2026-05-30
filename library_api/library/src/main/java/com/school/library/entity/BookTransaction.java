package com.school.library.entity;

import com.school.library.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "book_transactions")
public class BookTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Member member;

    private LocalDateTime issuedAt;

    private LocalDate dueDate;

    private LocalDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
