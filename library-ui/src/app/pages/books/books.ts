import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-books',
  imports: [CommonModule, FormsModule],
  templateUrl: './books.html',
  styleUrl: './books.css'
})
export class Books implements OnInit {

  books: any[] = [];
  searchKeyword = '';
  message = '';
  error = '';
  editingBookId: number | null = null;

  book: any = {
    title: '',
    author: '',
    isbn: '',
    category: '',
    totalCopies: 0,
    availableCopies: 0,
    shelfLocation: ''
  };

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
  this.message = '';
  this.error = '';

  this.bookService.getAllBooks().subscribe({
    next: (data) => {
      console.log('Books API response:', data);
      this.books = data || [];
    },
    error: (err) => {
      console.error(err);
      this.error = this.extractError(err);
    }
  });
}

  searchBooks(): void {
    if (!this.searchKeyword.trim()) {
      this.loadBooks();
      return;
    }

    this.bookService.searchBooks(this.searchKeyword).subscribe({
      next: data => this.books = data,
      error: err => this.error = this.extractError(err)
    });
  }

  saveBook(): void {
    this.message = '';
    this.error = '';

    if (this.editingBookId) {
      this.bookService.updateBook(this.editingBookId, this.book).subscribe({
        next: () => {
          this.message = 'Book updated successfully';
          this.resetForm();
          this.loadBooks();
        },
        error: err => this.error = this.extractError(err)
      });
    } else {
      this.bookService.createBook(this.book).subscribe({
        next: () => {
          this.message = 'Book created successfully';
          this.resetForm();
          this.loadBooks();
        },
        error: err => this.error = this.extractError(err)
      });
    }
  }

  editBook(b: any): void {
    this.editingBookId = b.id;
    this.book = { ...b };
  }

  deleteBook(id: number): void {
    if (confirm('Are you sure?')) {
      this.bookService.softDeleteBook(id).subscribe({
        next: () => {
          this.message = 'Book deleted successfully';
          this.loadBooks();
        },
        error: err => this.error = this.extractError(err)
      });
    }
  }

  resetForm(): void {
    this.editingBookId = null;
    this.book = {
      title: '',
      author: '',
      isbn: '',
      category: '',
      totalCopies: 0,
      availableCopies: 0,
      shelfLocation: ''
    };
  }

  extractError(err: any): string {
    if (err.error?.message) return err.error.message;
    if (typeof err.error === 'object') return Object.values(err.error).join(', ');
    return 'Something went wrong';
  }
}