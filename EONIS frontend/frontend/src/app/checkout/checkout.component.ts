import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../cart/cart.service';
import { OrderService } from '../orders/order.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {
  loading = false;

  // Forma za adresu
  order = {
    street: '',
    city: '',
    postalCode: '',
    country: '',
    note: ''
  };

  constructor(
    public cartService: CartService,
    private orderService: OrderService,
    private router: Router
  ) {}

  onSubmit() {
    const cartItems = this.cartService.getItems();
    if (cartItems.length === 0) {
      alert("Vaša korpa je prazna!");
      return;
    }

    const currentUser = JSON.parse(localStorage.getItem('user') || 'null');
    if (!currentUser) {
      alert("Morate biti ulogovani da biste poručili!");
      this.router.navigate(['/login']);
      return;
    }

    // DTO koji backend očekuje
    const dto = {
      userId: currentUser.id,
      street: this.order.street,
      city: this.order.city,
      postalCode: this.order.postalCode,
      country: this.order.country,
      note: this.order.note,
      items: cartItems.map(item => ({
        productId: item.product.id,
        quantity: item.quantity
      }))
    };

    this.loading = true;
    this.orderService.createOrder(dto).subscribe({
      next: () => {
        this.loading = false;
        alert('✅ Vaša porudžbina je uspešno kreirana!');
        this.cartService.clearCart();
        this.router.navigate(['/my-orders']); // prebaci ga odmah na Moje porudžbine
      },
      error: (err) => {
        this.loading = false;
        console.error(err);
        alert('❌ Došlo je do greške prilikom kreiranja porudžbine.');
      }
    });
  }

  getTotal(): number {
    return this.cartService.getItems()
      .reduce((acc, i) => acc + i.product.price * i.quantity, 0);
  }
}
