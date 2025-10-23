import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from './order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = 'http://localhost:8081/api/orders';

  constructor(private http: HttpClient) {}

  // Kreiranje nove porudžbine
  createOrder(productIds: number[]): Observable<string> {
    return this.http.post(this.apiUrl, { productIds }, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      },
      responseType: 'text'
    });
  }

  // Preuzimanje svih porudžbina za ulogovanog korisnika
  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.apiUrl, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    });
  }
}
