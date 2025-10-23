import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product.model';
import { CartService } from '../../cart/cart.service';


@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(
    private productService: ProductService,
    private cartService: CartService   // 👈 ubacili servis
  ) {}

  ngOnInit(): void {
    this.productService.getAll().subscribe({
      next: (data) => (this.products = data),
      error: (err) => console.error('Greška pri učitavanju proizvoda', err),
    });
  }

  addToCart(product: Product): void {
    this.cartService.addToCart(product);
    alert(`${product.name} je dodat u korpu!`);
  }
}
