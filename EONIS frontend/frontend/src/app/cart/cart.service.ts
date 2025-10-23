// src/app/cart.service.ts
import { Injectable } from '@angular/core';
import { Product } from '../products/product.model'; 

@Injectable({ providedIn: 'root' })
export class CartService {
  private items: Product[] = [];

  getItems(): any[] {
    const items = localStorage.getItem('cart');
    return items ? JSON.parse(items) : [];
  }

  addToCart(product: any) {
    this.items.push(product);
    this.saveItems(this.items);
  }
  

  clearCart(): Product[] {
    this.items = [];
    return this.items;
  }

  saveItems(items: any[]) {
    localStorage.setItem('cart', JSON.stringify(items));
  }
  
}
