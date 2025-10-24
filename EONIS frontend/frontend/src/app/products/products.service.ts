import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'http://localhost:8081/api/products';

  constructor(private http: HttpClient) {}

  // Vrati sve proizvode
  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  // Dodaj novi proizvod
  create(product: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, product);
  }

  // Izmeni proizvod
  update(id: number, product: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, product);
  }

  // Obriši proizvod
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
