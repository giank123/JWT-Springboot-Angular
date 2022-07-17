import { TokenService } from './../service/token.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProdGuardService implements CanActivate{

  realRol: string;

  constructor(
    private tokenService: TokenService,
    private router: Router
  ) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    //es un array
    const expectedRol = route.data.expectedRol;
    const roles = this.tokenService.getAuthorities();
    this.realRol = 'user';
    roles.forEach(rol => {
      if(rol === 'ROLE_ADMIN') {
        this.realRol = 'admin';
      }
    });
    
    //No hay token  y qe el expectedRol que es un array esta vacio y no lo encuentra
    // (-1 -> Significa que no esta en ninguna posicion)
    if(!this.tokenService.getToken() || expectedRol.indexOf(this.realRol) === -1) {
      this.router.navigate(['/']);
      return false;
    }
    
    return true;
  }
}
