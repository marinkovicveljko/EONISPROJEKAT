import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../orders/order.service';
import { Order } from '../../orders/order';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-admin-orders',
  templateUrl: './admin-orders.component.html',
  styleUrls: ['./admin-orders.component.css']
})
export class AdminOrdersComponent implements OnInit {
  orders: Order[] = [];
  editingOrder: Order | null = null;
  statusForm: FormGroup | null = null;

  // paging + sort
  totalElements = 0;
  page = 0;
  pageSize = 10;
  sort = 'createdDate,desc';

  constructor(private orderService: OrderService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.orderService.getOrders(this.page, this.pageSize, this.sort).subscribe({
      next: (res) => {
        this.orders = res.content || [];
        this.totalElements = res.totalElements;
      },
      error: (err) => console.error('Greška pri učitavanju porudžbina', err)
    });
  }

  startEdit(order: Order): void {
    this.editingOrder = order;
    this.statusForm = this.fb.group({ status: [order.status || ''] });
  }

  saveStatus(): void {
    if (this.editingOrder && this.statusForm) {
      const newStatus = this.statusForm.value.status;
      this.orderService.updateStatus(this.editingOrder.id!, newStatus).subscribe(() => {
        this.loadOrders();
        this.cancelEdit();
      });
    }
  }

  cancelEdit(): void {
    this.editingOrder = null;
    this.statusForm = null;
  }

  deleteOrder(id: number): void {
    if (confirm('Da li ste sigurni da želite da otkažete porudžbinu?')) {
      this.orderService.deleteOrder(id).subscribe(() => this.loadOrders());
    }
  }

  changePage(event: PageEvent) {
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadOrders();
  }

  setSort(field: string) {
    const [currField, currDir] = this.sort.split(',');
    const dir = (currField === field && currDir === 'asc') ? 'desc' : 'asc';
    this.sort = `${field},${dir}`;
    this.loadOrders();
  }
}
