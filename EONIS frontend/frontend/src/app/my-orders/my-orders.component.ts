import { Component, OnInit } from '@angular/core';
import { OrderService } from '../orders/order.service';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {
  orders: any[] = [];
  user: any;

  constructor(private orderService: OrderService, private auth: AuthService) {}

  ngOnInit(): void {
    this.user = this.auth.getCurrentUser();
    if (this.user) {
      this.orderService.getOrdersByUser(this.user.id).subscribe({
        next: (data) => {
          this.orders = data;
        },
        error: (err) => {
          console.error('❌ Greška pri učitavanju porudžbina:', err);
        }
      });
    }
  }
}
