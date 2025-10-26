import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8081/api/users';

  constructor(private http: HttpClient) {}

  // Vrati podatke o trenutnom korisniku (iz tokena)
  getProfile(): Observable<any> {
    return this.http.get(`${this.apiUrl}/me`);
  }

  // Promena lozinke
  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/change-password`, {
      oldPassword,
      newPassword
    });
  }

  // ✅ Sa pagingom i sortiranjem
  getAllUsers(page: number, size: number, sort: string): Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);
    return this.http.get<any>(this.apiUrl, { params });
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
