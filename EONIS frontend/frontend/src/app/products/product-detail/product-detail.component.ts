import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../product.service';
import { Product } from '../product.model';
import { CartService } from '../../cart/cart.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html'
})
export class ProductDetailComponent implements OnInit {
  product?: Product;
  errorMsg = '';

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    public cartService: CartService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getById(id).subscribe({
      next: data => this.product = data,
      error: err => this.errorMsg = 'Greška pri učitavanju proizvoda'
    });
  }



addToCart() {
  if (this.product) {
    this.cartService.addToCart(this.product);
    alert('Proizvod dodat u korpu!');
  }
}

}
