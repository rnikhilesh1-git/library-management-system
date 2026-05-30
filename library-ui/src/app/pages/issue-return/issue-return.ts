import { Component, OnInit } from '@angular/core';
import { BookService } from '../../services/book.service';
import { MemberService } from '../../services/member.service';
import { LibraryTransactionService } from '../../services/library-transaction.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-issue-return',
  imports: [CommonModule, FormsModule],
  templateUrl: './issue-return.html',
  styleUrl: './issue-return.css'
})
export class IssueReturn implements OnInit {

  books: any[] = [];
  members: any[] = [];
  transactions: any[] = [];

  selectedBookId: number | null = null;
  selectedMemberId: number | null = null;
  selectedTransactionId: number | null = null;

  message = '';
  error = '';

  constructor(
    private bookService: BookService,
    private memberService: MemberService,
    private transactionService: LibraryTransactionService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.bookService.getAllBooks().subscribe(data => this.books = data);
    this.memberService.getAllMembers().subscribe(data => this.members = data);
    this.transactionService.getTransactionHistory().subscribe(data => this.transactions = data);
  }

  issueBook(): void {
    this.message = '';
    this.error = '';

    if (!this.selectedBookId || !this.selectedMemberId) {
      this.error = 'Please select book and member';
      return;
    }

    this.transactionService.issueBook({
      bookId: this.selectedBookId,
      memberId: this.selectedMemberId
    }).subscribe({
      next: () => {
        this.message = 'Book issued successfully';
        this.selectedBookId = null;
        this.selectedMemberId = null;
        this.loadData();
      },
      error: err => this.error = this.extractError(err)
    });
  }

  returnBook(): void {
    this.message = '';
    this.error = '';

    if (!this.selectedTransactionId) {
      this.error = 'Please select transaction';
      return;
    }

    this.transactionService.returnBook(this.selectedTransactionId).subscribe({
      next: () => {
        this.message = 'Book returned successfully';
        this.selectedTransactionId = null;
        this.loadData();
      },
      error: err => this.error = this.extractError(err)
    });
  }

  getIssuedTransactions(): any[] {
    return this.transactions.filter(t => t.status === 'ISSUED');
  }

  extractError(err: any): string {
    if (err.error?.message) return err.error.message;
    if (typeof err.error === 'object') return Object.values(err.error).join(', ');
    return 'Something went wrong';
  }
}