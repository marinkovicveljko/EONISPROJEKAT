import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl = 'http://localhost:8081/api/orders'; // Backend URL

  constructor(private http: HttpClient) {}

  // Kreiranje nove porudžbine
  createOrder(orderDto: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, orderDto);
  }

  // Preuzimanje svih porudžbina (npr. za admin ili pregled narudžbina korisnika)
  getOrders(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Pretraga po korisniku
  getOrdersByUser(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/search/by-user?userId=${userId}`);
  }

  // Pretraga po statusu
  getOrdersByStatus(status: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/search/by-status?status=${status}`);
  }
}
