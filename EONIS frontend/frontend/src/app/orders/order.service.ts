import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from './order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = 'http://localhost:8081/api/orders';

  constructor(private http: HttpClient) {}

  createOrder(orderDto: any): Observable<Order> {
    return this.http.post<Order>(this.apiUrl, orderDto);
  }

  // ✅ Sve porudžbine sa pagingom i sortiranjem
  getOrders(page: number, size: number, sort: string): Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);
    return this.http.get<any>(this.apiUrl, { params });
  }

  getOrdersByUser(userId: number, page: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<any>(`${this.apiUrl}/search/by-user?userId=${userId}`, { params });
  }

  getOrdersByStatus(status: string, page: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);
    return this.http.get<any>(`${this.apiUrl}/search/by-status?status=${status}`, { params });
  }

  updateStatus(id: number, status: string): Observable<Order> {
    return this.http.put<Order>(`${this.apiUrl}/${id}/status`, { status });
  }

  deleteOrder(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
