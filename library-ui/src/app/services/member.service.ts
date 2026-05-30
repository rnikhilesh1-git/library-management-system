import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MemberService {

  private apiUrl = 'http://localhost:8080/api/v1/members';

  constructor(private http: HttpClient) {}

  getAllMembers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/list`);
  }

  createMember(member: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, member);
  }

  updateMember(memberId: number, member: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${memberId}/update`, member);
  }

  deactivateMember(memberId: number): Observable<any> {
    return this.http.patch(`${this.apiUrl}/${memberId}/deactivate`, {});
  }
}