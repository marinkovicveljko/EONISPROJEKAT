import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../products/product.service';

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})
export class AdminProductsComponent implements OnInit {
  products: any[] = [];
  productForm: FormGroup;
  editingProduct: any = null;

  constructor(private productService: ProductService, private fb: FormBuilder) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: [0, Validators.required],
      stock: [0, Validators.required],
      categoryId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAll().subscribe(res => this.products = res);
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
        this.productForm.reset();
      });
    }
  }

  edit(product: any): void {
    this.editingProduct = product;
    this.productForm.patchValue(product);
  }

  cancelEdit(): void {
    this.editingProduct = null;
    this.productForm.reset();
  }

  delete(id: number): void {
    if (confirm('Da li ste sigurni da želite da obrišete proizvod?')) {
      this.productService.delete(id).subscribe(() => this.loadProducts());
    }
  }
}
