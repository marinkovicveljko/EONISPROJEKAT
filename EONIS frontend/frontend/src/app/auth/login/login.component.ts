import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { ProductsService } from '../../products/products.service'; // 👈 dodaj servis za test API-ja

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  loading = false;
  errorMsg = '';

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private products: ProductsService // 👈 ubaci ga u konstruktor
  ) {}

  submit() {
    this.errorMsg = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { email, password } = this.form.value;
    this.loading = true;

    this.auth.login(email!, password!).subscribe({
      next: token => {
        this.loading = false;
        localStorage.setItem('token', token); // čuvamo JWT
        console.log('Token sa backenda:', token);
        alert('Login uspešan!');
        this.router.navigateByUrl('/'); // za sada na početnu
      },
      error: err => {
        this.loading = false;
        this.errorMsg = typeof err?.error === 'string' ? err.error : 'Login failed';
      }
    });
  }

  // 👇 Dodaj test metodu
  testApi() {
    this.products.getAll().subscribe({
      next: data => {
        console.log('PROIZVODI:', data);
        alert('Poziv /api/products uspeo! (vidi Console)');
      },
      error: err => {
        console.error('Greška:', err);
        alert('Neuspešan poziv /api/products (vidi Console)');
      }
    });
  }
}
