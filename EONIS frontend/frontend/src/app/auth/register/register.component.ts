import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'] 
})
export class RegisterComponent {
  loading = false;
  errorMsg = '';
  successMsg = '';

  form = this.fb.group({
    name: ['', Validators.required],
    surname: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4)]],
    role: ['USER', Validators.required] // podrazumevano USER
  });

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {}

  submit() {
    this.errorMsg = '';
    this.successMsg = '';
  
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
  
    const payload = this.form.value as {
      name: string;
      surname: string;
      email: string;
      password: string;
      role: string;
    };
  
    this.auth.register(payload).subscribe({
      next: res => {
        console.log('✅ Registracija uspešna:', res);
  
        // 👇 odmah logujemo korisnika
        this.auth.login({ email: payload.email, password: payload.password }).subscribe({
          next: (loginRes) => {
            if (loginRes.token) {
              localStorage.setItem('token', loginRes.token);
            }
            if (loginRes.user) {
              localStorage.setItem('user', JSON.stringify(loginRes.user));
            }
  
            alert('✅ Dobrodošli, ' + payload.name + '!');
            this.router.navigateByUrl('/'); // prebaci na glavnu
          },
          error: (err) => {
            console.error('❌ Greška pri automatskom loginu:', err);
            alert('Registracija je uspela, ali morate da se prijavite ručno.');
            this.router.navigateByUrl('/login');
          }
        });
      },
      error: err => {
        this.errorMsg = typeof err?.error === 'string' ? err.error : 'Registracija neuspešna';
        console.error('❌ Greška:', err);
      }
    });
  }
  
  }
  
