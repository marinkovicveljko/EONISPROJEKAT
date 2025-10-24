import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../users/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: any = null;                 
  showPasswordForm = false;         
  passwordForm: FormGroup;          
  errorMsg = '';
  successMsg = '';

  constructor(private userService: UserService, private fb: FormBuilder) {
    this.passwordForm = this.fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    this.loadUser();
  }

  // učitavanje trenutnog korisnika sa bekenda
  loadUser(): void {
    this.userService.getProfile().subscribe({
      next: (res) => {
        this.user = res;
      },
      error: (err) => {
        console.error('Greška prilikom učitavanja korisnika:', err);
        this.errorMsg = 'Nije moguće učitati korisnika';
      }
    });
  }

  // otvori/zatvori formu
  togglePasswordForm(): void {
    this.showPasswordForm = !this.showPasswordForm;
    this.successMsg = '';
    this.errorMsg = '';
    this.passwordForm.reset();
  }

  // promena lozinke
  changePassword(): void {
    if (this.passwordForm.invalid) {
      this.errorMsg = 'Unesite obe lozinke.';
      return;
    }

    const { oldPassword, newPassword } = this.passwordForm.value;

    this.userService.changePassword(oldPassword!, newPassword!).subscribe({
      next: () => {
        this.successMsg = 'Lozinka uspešno promenjena ✅';
        this.togglePasswordForm();
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Došlo je do greške pri promeni lozinke';
      }
    });
  }
}
