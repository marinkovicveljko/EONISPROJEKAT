import { Component, OnInit } from '@angular/core';
import { Product } from '../products/product.model';
import { ProductService } from '../products/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  products: Product[] = [];
  loading = true;

  // 👇 za paginaciju
  page = 0;
  size = 10;
  sort = 'id,asc';
  totalPages = 0;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.productService.getAll(this.page, this.size, this.sort).subscribe({
      next: data => {
        this.products = data.content;   // lista proizvoda
        this.totalPages = data.totalPages; // ukupno strana
        this.loading = false;
      },
      error: err => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadProducts();
    }
  }

  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadProducts();
    }
  }
}
