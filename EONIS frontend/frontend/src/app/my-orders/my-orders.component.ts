import { Component, OnInit } from '@angular/core';
import { Order } from '../orders/order';
import { OrderService } from '../orders/order.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {
  orders: Order[] = [];
  loading = true;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders() {
    this.loading = true;

    // ✅ Preuzmi userId iz localStorage (setuješ ga kad se user uloguje)
    const currentUser = JSON.parse(localStorage.getItem('user') || 'null');
    if (!currentUser || !currentUser.id) {
      console.error('❌ Nema user-a u localStorage');
      this.orders = [];
      this.loading = false;
      return;
    }

    this.orderService.getOrdersByUser(currentUser.id).subscribe({
      next: (orders) => {
        this.orders = orders || [];
        this.loading = false;
      },
      error: (err) => {
        console.error('❌ Greška pri učitavanju porudžbina:', err);
        this.orders = [];
        this.loading = false;
      }
    });
  }
}
