import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../products/product.service';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})
export class AdminProductsComponent implements OnInit {
  products: any[] = [];
  productForm: FormGroup;
  editingProduct: any = null;

  // paging + sort
  totalElements = 0;
  page = 0;
  pageSize = 10;
  sort = 'id,desc';

  constructor(private productService: ProductService, private fb: FormBuilder) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(1)]],
      stock: ['', [Validators.required, Validators.min(0)]],
      imageUrl: ['', Validators.required],
      categoryId: [1]
    });
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAll(this.page, this.pageSize, this.sort).subscribe({
      next: (res) => {
        this.products = res.content;
        this.totalElements = res.totalElements;
      },
      error: (err) => console.error('Greška pri učitavanju proizvoda', err)
    });
  }

  submit(): void {
    if (this.productForm.invalid) return;
    const product = this.productForm.value;

    if (this.editingProduct) {
      this.productService.update(this.editingProduct.id, product).subscribe(() => {
        this.loadProducts();
        this.cancelEdit();
      });
    } else {
      this.productService.create(product).subscribe(() => {
        this.loadProducts();
        this.productForm.reset({
          name: '', description: '', price: '', stock: '', imageUrl: '', categoryId: 1
        });
      });
    }
  }

  edit(product: any): void {
    this.editingProduct = product;
    this.productForm.patchValue(product);
  }

  cancelEdit(): void {
    this.editingProduct = null;
    this.productForm.reset({ name: '', description: '', price: '', stock: '', imageUrl: '', categoryId: 1 });
  }

  delete(id: number): void {
    if (confirm('Da li ste sigurni da želite da obrišete proizvod?')) {
      this.productService.delete(id).subscribe(() => this.loadProducts());
    }
  }

  changePage(event: PageEvent) {
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadProducts();
  }

  setSort(field: string) {
    const [currField, currDir] = this.sort.split(',');
    const dir = (currField === field && currDir === 'asc') ? 'desc' : 'asc';
    this.sort = `${field},${dir}`;
    this.loadProducts();
  }
}
