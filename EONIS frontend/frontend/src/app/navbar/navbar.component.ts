import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { SearchService } from '../shared/search.service'; // dodaj ovo

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  searchTerm: string = '';

  constructor(private auth: AuthService, private searchService: SearchService) {}

  get user(): any {
    return this.auth.getCurrentUser();
  }

  get isLoggedIn(): boolean {
    return this.auth.isLoggedIn();
  }

  get isAdmin(): boolean {
    return this.user?.role === 'ADMIN';
  }

  get isUser(): boolean {
    return this.user?.role === 'USER';
  }

  logout() {
    this.auth.logout();
    window.location.reload();
  }

  onSearch() {
    this.searchService.setSearchTerm(this.searchTerm);
  }
}
