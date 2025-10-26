import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product.model';
import { CartService } from '../../cart/cart.service';
import { SearchService } from '../../shared/search.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'] 
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];

  // paginacija i sortiranje
  page = 0;
  size = 12;
  totalElements = 0;
  sort = 'name,asc';

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private searchService: SearchService
  ) {}

  ngOnInit(): void {
    this.fetchProducts();
    this.searchService.searchTerm$.subscribe(term => this.applyFilter(term));
  }

  fetchProducts() {
    this.productService.getAll(this.page, this.size, this.sort).subscribe({
      next: (res) => {
        this.products = res.content || [];
        this.filteredProducts = this.products;
        this.totalElements = res.totalElements;
      },
      error: (err) => console.error('Greška pri učitavanju proizvoda', err),
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

  changePage(event: any) {
    this.page = event.pageIndex;
    this.size = event.pageSize;
    this.fetchProducts();
  }

  changeSort(sortField: string) {
    this.sort = sortField;
    this.fetchProducts();
  }
}
