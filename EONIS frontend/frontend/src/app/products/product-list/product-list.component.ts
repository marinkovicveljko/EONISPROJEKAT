import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product.model';
import { CartService } from '../../cart/cart.service';
import { SearchService } from '../../shared/search.service'; // dodaj ovo

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'] 
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = []; 

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private searchService: SearchService  
  ) {}

  ngOnInit(): void {
    this.productService.getAll().subscribe({
      next: (data) => {
        this.products = data;
        this.filteredProducts = data; 
      },
      error: (err) => console.error('Greška pri učitavanju proizvoda', err),
    });

    
    this.searchService.searchTerm$.subscribe(term => {
      this.applyFilter(term);
    });
  }

  applyFilter(term: string): void {
    const lower = term.toLowerCase();
    this.filteredProducts = this.products.filter(
      p =>
        p.name.toLowerCase().includes(lower) ||
        p.description.toLowerCase().includes(lower)
    );
  }

  addToCart(product: Product): void {
    this.cartService.addToCart(product);
    alert(`${product.name} je dodat u korpu!`);
  }
}
