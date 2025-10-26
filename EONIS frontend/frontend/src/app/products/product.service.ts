import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'http://localhost:8081/api/products';

  constructor(private http: HttpClient) {}

  // ✅ Svi proizvodi sa pagingom i sortiranjem
  getAll(page: number, size: number, sort: string): Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);
    return this.http.get<any>(this.baseUrl, { params });
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  create(product: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, product);
  }

  update(id: number, product: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, product);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getProductById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }
}
