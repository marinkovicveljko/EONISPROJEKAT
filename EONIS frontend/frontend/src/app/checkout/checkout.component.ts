import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../cart/cart.service';
import { OrderService } from '../orders/order.service';
import { PaymentService } from '../payments/payment.service';
import { loadStripe, Stripe, StripeCardElement } from '@stripe/stripe-js';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements AfterViewInit {
  loading = false;
  stripe!: Stripe | null;
  card!: StripeCardElement;

  order = {
    street: '',
    city: '',
    postalCode: '',
    country: '',
    note: ''
  };

  constructor(
    public cartService: CartService,
    private orderService: OrderService,
    private router: Router,
    private paymentService: PaymentService
  ) {}

  async ngAfterViewInit() {
    // publishable key (ostavi svoj)
    this.stripe = await loadStripe('pk_test_51SMXDq3bbNIOwFjnfNISfRnFxKEtGYUQQvWqKKxMd4hCSZ6W8eBg10LLCopGLtWncf6jRdWSRmsoNvaCZi8f6pik00keAMslxA');
    if (this.stripe) {
      const elements = this.stripe.elements();

      // ✅ hidePostalCode: true -> uklanja postal code polje iz Stripe card elementa
      this.card = elements.create('card', { hidePostalCode: true });

      // čekamo malo da Angular renderuje DOM pa montiramo
      setTimeout(() => {
        const cardDiv = document.getElementById('card-element');
        if (cardDiv) {
          this.card.mount('#card-element');
        }
      }, 300);
    }
  }

  onSubmit() {
    const cartItems = this.cartService.getItems();
    if (cartItems.length === 0) {
      alert("Vaša korpa je prazna!");
      return;
    }

    const currentUser = JSON.parse(localStorage.getItem('user') || 'null');
    if (!currentUser) {
      alert("Morate biti ulogovani da biste poručili!");
      this.router.navigate(['/login']);
      return;
    }

    const dto = {
      userId: currentUser.id,
      street: this.order.street,
      city: this.order.city,
      postalCode: this.order.postalCode,
      country: this.order.country,
      note: this.order.note,
      items: cartItems.map(item => ({
        productId: item.product.id,
        quantity: item.quantity
      }))
    };

    this.loading = true;
    this.orderService.createOrder(dto).subscribe({
      next: (order) => {
        this.paymentService.checkout(order.id, this.getTotal()).subscribe({
          next: async (res: any) => {
            if (!this.stripe) return;

            const clientSecret = res.clientSecret;

            const { error } = await this.stripe.confirmCardPayment(clientSecret, {
              payment_method: {
                card: this.card
              }
            });

            if (error) {
              alert('❌ Greška prilikom plaćanja: ' + error.message);
            } else {
              alert('✅ Plaćanje uspešno!');
              this.cartService.clearCart();
              this.router.navigate(['/my-orders']);
            }
            this.loading = false;
          },
          error: (err) => {
            this.loading = false;
            console.error(err);
            alert('❌ Greška u Stripe checkout-u.');
          }
        });
      },
      error: (err) => {
        this.loading = false;
        console.error(err);
        alert('❌ Došlo je do greške prilikom kreiranja porudžbine.');
      }
    });
  }

  getTotal(): number {
    return this.cartService.getItems()
      .reduce((acc, i) => acc + i.product.price * i.quantity, 0);
  }
}
