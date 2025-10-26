import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const user = this.auth.getCurrentUser();

    if (!user) {
      // ako nije ulogovan, vrati ga na login
      this.router.navigate(['/login']);
      return false;
    }

    // ako ruta ima zahtevanu ulogu (npr. ADMIN)
    const expectedRole = route.data['role'];
    if (expectedRole && user.role !== expectedRole) {
      // ako nije odgovarajuća uloga
      this.router.navigate(['/']);
      return false;
    }

    return true;
  }
}
