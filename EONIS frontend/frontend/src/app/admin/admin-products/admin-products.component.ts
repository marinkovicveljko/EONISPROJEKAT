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
      price: ['', [Validators.required, Validators.min(1)]],   // 👈 prazno umesto 0
      stock: ['', [Validators.required, Validators.min(0)]],   // 👈 prazno umesto 0
      imageUrl: ['', Validators.required],
      categoryId: [1] // default 1
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
      // update
      this.productService.update(this.editingProduct.id, product).subscribe(() => {
        this.loadProducts();
        this.cancelEdit();
      });
    } else {
      // create
      this.productService.create(product).subscribe(() => {
        this.loadProducts();
        this.productForm.reset({
          name: '',
          description: '',
          price: '',  // 👈 prazno
          stock: '',  // 👈 prazno
          imageUrl: '',
          categoryId: 1
        });
      });
    }
  }

  edit(product: any): void {
    this.editingProduct = product;
    this.productForm.patchValue({
      name: product.name,
      description: product.description,
      price: product.price,
      stock: product.stock,
      imageUrl: product.imageUrl,
      categoryId: 1
    });
  }

  cancelEdit(): void {
    this.editingProduct = null;
    this.productForm.reset({
      name: '',
      description: '',
      price: '',
      stock: '',
      imageUrl: '',
      categoryId: 1
    });
  }

  delete(id: number): void {
    if (confirm('Da li ste sigurni da želite da obrišete proizvod?')) {
      this.productService.delete(id).subscribe(() => this.loadProducts());
    }
  }
}
