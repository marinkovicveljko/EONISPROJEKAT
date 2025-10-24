import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { ProductsService } from '../../products/products.service';

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
    private products: ProductsService
  ) {}

  submit() {
    this.errorMsg = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { email, password } = this.form.value;
    this.loading = true;

    this.auth.login({ email: email!, password: password! }).subscribe({
      next: (res) => {
        this.loading = false;

        // Ako backend vraća { token, user }
        if (res.token) {
          localStorage.setItem('token', res.token);
        }

        if (res.user) {
          localStorage.setItem('user', JSON.stringify(res.user));
          console.log('Ulogovani korisnik:', res.user);
        }

        alert('✅ Login uspešan!');
        this.router.navigateByUrl('/');
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = typeof err?.error === 'string' ? err.error : 'Login failed';
      }
    });
  }

  // 👇 test API metoda
  testApi() {
    this.products.getAll().subscribe({
      next: (data) => {
        console.log('PROIZVODI:', data);
        alert('Poziv /api/products uspeo! (vidi Console)');
      },
      error: (err) => {
        console.error('Greška:', err);
        alert('Neuspešan poziv /api/products (vidi Console)');
      }
    });
  }
}
