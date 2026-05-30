import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class LibraryTransactionService {

  private apiUrl = 'http://localhost:8080/api/v1/library-transactions';

  constructor(private http: HttpClient) {}

  issueBook(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/issue-book`, data);
  }

  returnBook(transactionId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${transactionId}/return-book`, {});
  }

  getTransactionHistory(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/history`);
  }
}