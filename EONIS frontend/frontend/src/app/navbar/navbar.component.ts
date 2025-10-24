import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  user: any = null;

  constructor(private auth: AuthService) {}

  ngOnInit() {
    this.user = this.auth.getCurrentUser();
  }

  get isLoggedIn(): boolean {
    return this.auth.isLoggedIn();
  }

  logout() {
    this.auth.logout();
    window.location.reload(); // ili this.user = null; this.router.navigate(['/']);
  }
}
