import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private apiUrl = 'http://localhost:8081/api/payments';

  constructor(private http: HttpClient) {}

  // Poziv backendu za checkout
  checkout(orderId: number, amount: number): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/checkout`, {
      orderId,
      amount,
      currency: 'rsd',
      paymentMethod: 'CARD'
    });
  }

  // Dobavljanje svih plaćanja (za admina)
  getAllPayments(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
