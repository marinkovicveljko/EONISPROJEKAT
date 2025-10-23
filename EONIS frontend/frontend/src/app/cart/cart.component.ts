import { Component, OnInit } from '@angular/core';
import { CartService } from './cart.service';
import { Product } from '../products/product.model';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html'
})
export class CartComponent {
  items = this.cartService.getItems();

  constructor(private cartService: CartService) {}

  get totalPrice(): number {
    return this.items.reduce((sum, item) => sum + item.price, 0);
  }

  removeFromCart(productId: number) {
    this.items = this.items.filter(p => p.id !== productId);
    this.cartService.saveItems(this.items); // osveži lokalno skladište u servisu
  }

  clearCart() {
    this.cartService.clearCart();
    this.items = [];
  }

  checkout() {
    const productIds = this.items.map(p => p.id);
  
    // Ako je korpa prazna, ne dozvoljavaj
    if (productIds.length === 0) {
      alert('Korpa je prazna!');
      return;
    }
    fetch('http://localhost:8081/api/orders', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    },
    body: JSON.stringify(productIds)
  })
    .then(res => {
      if (!res.ok) throw new Error('Greška prilikom slanja porudžbine');
      return res.text();
    })
    .then(msg => {
      alert(msg);
      this.clearCart(); // isprazni korpu nakon narudžbine
    })
    .catch(err => {
      alert(err.message);
    });
}
}
