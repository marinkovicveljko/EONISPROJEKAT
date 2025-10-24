import { Component, OnInit } from '@angular/core';
import { OrderService } from './order.service';
import { Order } from './order';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrderComponent implements OnInit {
  orders: Order[] = [];
  loading = true;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    const currentUser = JSON.parse(localStorage.getItem('user') || 'null');

    if (!currentUser) {
      this.loading = false;
      return;
    }

    this.orderService.getOrdersByUser(currentUser.id).subscribe({
      next: (res) => {
        this.orders = res;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }
}
