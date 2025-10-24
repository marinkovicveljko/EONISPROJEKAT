import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../products/product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private items: { product: Product; quantity: number }[] = [];

  constructor(private router: Router) {}

  addToCart(product: Product) {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('⚠️ Morate se prvo ulogovati da bi dodali u korpu.');
      this.router.navigate(['/login']);
      return;
    }

    const existing = this.items.find(i => i.product.id === product.id);
    if (existing) {
      existing.quantity++;
    } else {
      this.items.push({ product, quantity: 1 });
    }
  }

  getItems() {
    return this.items;
  }

  clearCart() {
    this.items = [];
    return this.items;
  }

    // 👇 dodajemo metodu za brisanje proizvoda iz korpe
    removeItem(productId: number) {
      this.items = this.items.filter(i => i.product.id !== productId);
    }
  
    // 👇 dodajemo metodu za ukupnu cenu
    getTotal(): number {
      return this.items.reduce((acc, i) => acc + i.product.price * i.quantity, 0);
    }
}
