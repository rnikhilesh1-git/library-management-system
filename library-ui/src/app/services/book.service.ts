import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class BookService {

  private apiUrl = 'http://localhost:8080/api/v1/books';

  constructor(private http: HttpClient) {}

  getAllBooks(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/list`);
  }

  searchBooks(keyword: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/search?keyword=${keyword}`);
  }

  createBook(book: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, book);
  }

  updateBook(bookId: number, book: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${bookId}/update`, book);
  }

  softDeleteBook(bookId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${bookId}/soft-delete`, {
      responseType: 'text'
    });
  }
}