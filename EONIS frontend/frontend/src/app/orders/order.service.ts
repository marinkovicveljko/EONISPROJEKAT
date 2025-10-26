import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from './order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl = 'http://localhost:8081/api/orders'; // Backend URL

  constructor(private http: HttpClient) {}

  // Kreiranje nove porudžbine
  createOrder(orderDto: any): Observable<Order> {
    return this.http.post<Order>(this.apiUrl, orderDto);
  }

  // Preuzimanje svih porudžbina
  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.apiUrl);
  }

  // Pretraga po korisniku
  getOrdersByUser(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/search/by-user?userId=${userId}`);
  }

  // Pretraga po statusu
  getOrdersByStatus(status: string): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/search/by-status?status=${status}`);
  }

  // ✅ Admin: promena statusa
  updateStatus(id: number, status: string): Observable<Order> {
    return this.http.put<Order>(`${this.apiUrl}/${id}/status`, { status });
  }

  // ✅ Admin: otkazivanje porudžbine
  deleteOrder(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
