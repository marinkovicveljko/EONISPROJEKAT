import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface RegisterPayload {
  name: string;
  surname: string;
  email: string;
  password: string;
  role: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8081/api/auth';

  constructor(private http: HttpClient) {}

  
  login(email: string, password: string): Observable<string> {
    return this.http.post(this.baseUrl + '/login', { email, password }, { responseType: 'text' })
      .pipe(tap(token => localStorage.setItem('token', token)));
  }
  
  
  register(payload: RegisterPayload): Observable<string> {
    return this.http.post(this.baseUrl + '/register', payload, { responseType: 'text' });
  }
  
  logout(): void {
    localStorage.removeItem('token');
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.token;
  }
}
