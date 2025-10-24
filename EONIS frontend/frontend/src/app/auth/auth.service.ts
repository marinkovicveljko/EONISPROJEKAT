import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8081/api/auth';

  constructor(private http: HttpClient) {}

  // 👇 login prima jedan objekat (credentials)
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, credentials);
  }

  // opcionalno: metoda za registraciju
  register(data: { name: string; surname: string; email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, data);
  }

  // 👇 helper za proveru da li je korisnik ulogovan
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  // 👇 helper za logout
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

  getCurrentUser(): any {
    const raw = localStorage.getItem('user');
    try { return raw ? JSON.parse(raw) : null; } catch { return null; }
  }
  
  
}
