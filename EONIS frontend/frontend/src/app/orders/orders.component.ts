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

  // za paginaciju
  page = 0;
  size = 10;
  totalElements = 0;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    const currentUser = JSON.parse(localStorage.getItem('user') || 'null');
    if (!currentUser) {
      this.loading = false;
      return;
    }
    this.fetchOrders(currentUser.id);
  }

  fetchOrders(userId: number) {
    this.loading = true;
    this.orderService.getOrdersByUser(userId, this.page, this.size).subscribe({
      next: (res) => {
        this.orders = res.content || [];
        this.totalElements = res.totalElements;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  changePage(event: any) {
    this.page = event.pageIndex;
    this.size = event.pageSize;
    const currentUser = JSON.parse(localStorage.getItem('user') || 'null');
    if (currentUser) {
      this.fetchOrders(currentUser.id);
    }
  }
}
