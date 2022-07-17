# CrudFRONT

## IMPLEMENTAR

### En app-routing
 ```bash
  {path: '', component: IndexComponent},
  {path: 'login', component: LoginComponent},
  {path: 'registro', component: RegistroComponent},
  {path: 'lista', component: ListaProductoComponent},
 ```

### En models
1. NuevoUsuario
```bash
  export class NuevoUsuario {
    nombre: string;
    nombreusuario: string;
    email: string;
    password: string;
    authorities: String[];
  }
 ```
 2. LoginUsuario
 ```bash
  export class LoginUsuario {
    nombreusuario: string;
    password: string;

    constructor(nombreUsuario: string, password:string) {
        this.nombreusuario = nombreUsuario;
        this.password = password;
  }
}
 ```
 3. JwtDTO
 ```bash
  export class JwtDTO {
    token: string;
    type: string;
    nombreUsuario: string;
    authorities: string[];
}
 ```

 ### En Service
1. AuthService
```bash
  export class AuthService {

  authURL = 'http://localhost:8080/auth/';

  constructor(private httpClient: HttpClient) { }

  public nuevo(nuevoUsuario: NuevoUsuario): Observable<any> {
    return this.httpClient.post<any>(this.authURL + 'nuevo', nuevoUsuario);
  }

  public login(loginUsuario: LoginUsuario): Observable<JwtDTO> {
    return this.httpClient.post<JwtDTO>(this.authURL + 'login', loginUsuario);
  }
}
 ```
 2. TokenService
 ```bash
  import { Injectable } from '@angular/core';

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUserName';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  roles: Array<string> = [];

  constructor() { }

  public setToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public setUserName(userName: string): void {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, userName);
  }

  public getUserName(): string {
    return sessionStorage.getItem(USERNAME_KEY);
  }

  public setAuthorities(authorities: string[]): void {
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  public getAuthorities(): string[] {
    this.roles = [];
    if (sessionStorage.getItem(AUTHORITIES_KEY)) {
      JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
        this.roles.push(authority.authority);
      });
    }
    return this.roles;
  }

//Limpiar el storage
  public logOut(): void {
    window.sessionStorage.clear();
  }
}
 ```

 ### En auth
 #### En auth/Login
 1. Conponent.ts
 ```bash
   export class LoginComponent implements OnInit {

  isLogged = false;
  isLoginFail = false;
  loginUsuario: LoginUsuario;
  nombreUsuario: string;
  password: string;
  roles: string[] = [];
  errMsj: string;

  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.isLoginFail = false;
      this.roles = this.tokenService.getAuthorities();
    }
  }


  onLogin(): void {
    this.loginUsuario = new LoginUsuario(this.nombreUsuario, this.password);
    this.authService.login(this.loginUsuario).subscribe(
      data => {
        this.isLogged = true;

        this.tokenService.setToken(data.token);
        this.tokenService.setUserName(data.nombreUsuario);
        this.tokenService.setAuthorities(data.authorities);
        this.roles = data.authorities;
        
        this.router.navigate(['/']);
      },
      err => {
        this.isLogged = false;
        this.errMsj = err.error.message;
        console.log(err.error.message);
      }
    );
  }

}
 ```

  2. Conponent.html
 ```bash
   <div class="text-center" *ngIf="isLogged; else loggedOut">
    <h2>Ya has iniciado sesión</h2>
   </div>

  <ng-template #loggedOut>
    <div class="container d-flex justify-content-center">
        <div class="card">
            <div class="card-header">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                      <a class="nav-link active" routerLink="/login">Iniciar Sesión</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" routerLink="/registro">Crear Cuenta</a>
                    </li>
                    
                </ul>
            </div>
            <div class="card-body"></div>
        </div>
    </div>
  </ng-template>
 ```

  #### En auth/Registro
```bash
    <div class="text-center" *ngIf="isLogged; else loggedOut">
    <h2>Ya has iniciado sesión</h2>
</div>

<ng-template #loggedOut>
    <div class="container d-flex justify-content-center">
        <div class="card">
            <div class="card-header">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                      <a class="nav-link" routerLink="/login">Iniciar Sesión</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link active" routerLink="/registro">Crear Cuenta</a>
                    </li>
                    
                </ul>
            </div>
            <div class="card-body"></div>
        </div>
    </div>
</ng-template>
```

  #### En Menu

1. component.html

```bash
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link" routerLink="/"><i class="fas fa-home"></i> Inicio</a>
          </li>
        <li class="nav-item" *ngIf="isLogged">
          <a class="nav-link" routerLink="/lista"><i class="fas fa-list-alt"></i> Lista</a>
        </li>
        <li class="nav-item" *ngIf="!isLogged">
          <a class="nav-link"  routerLink="/login"><i class="fas fa-sign-in-alt"></i> Iniciar Sesion</a>
        </li>
        <li class="nav-item" *ngIf="isLogged"  (click)="onLogout()">
           <a class="nav-link" style="cursor: pointer;"><i class="fas fa-door-open"></i> Cerrar Sesion</a>
        </li>
      </ul>
    </div>
  </nav>
```

2. component.ts
```bash
   export class MenuComponent implements OnInit {

  isLogged = false;

  constructor(private tokenService: TokenService) { }

  ngOnInit() {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
    } else {
      this.isLogged = false;
    }
  }

  onLogout(): void {
    this.tokenService.logOut();
    //como un F5 (refresca la pagina)
    window.location.reload();
  }

}

```

  #### En Index
  1. component.html

```bash
<app-menu></app-menu>
<div class="container text-center">
    <div *ngIf="isLogged; else loggedOut"><h2>Bienvenido {{nombreUsuario}}</h2></div>
    <ng-template #loggedOut>No has iniciado Sesión</ng-template>
</div>

```

  2. component.ts
  
```bash
export class IndexComponent implements OnInit {

  isLogged = false;
  nombreUsuario = '';

  constructor(private tokenService: TokenService) { }

  ngOnInit() {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.nombreUsuario = this.tokenService.getUserName();
    } else {
      this.isLogged = false;
      this.nombreUsuario = '';
    }
  }

}
```

  #### En Interceptors
  Se pone en medio del request y el backend , cada peticion del cliente
   lo intercepta y comprueba que haya token en el sessionStorage del navegador
   y se los adjunta. El backend comprueba que es correcto y devuelve una respuesta al 
   response en caso contrario manda un error

  1. component.ts

```bash
  export class ProdInterceptorsService implements HttpInterceptor{

  constructor(private tokenService: TokenService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let intReq =  req;
    const token = this.tokenService.getToken();
    if(token != null) {
      intReq = req.clone({ headers: req.headers.set('Authorization', 'Bearer '+token) });
    }

    return next.handle(intReq);

  }
}

export const interceptorProvider = [{provide: HTTP_INTERCEPTORS, useClass: ProdInterceptorsService, multi: true}];
```

 #### En app.module
```bash
import { interceptorProvider } from './interceptors/prod-interceptors.service';

 providers: [interceptorProvider],
```

  #### En Guard
1.   Para controlar a que rutas puedes acceder o no dependiendo de los roles de cada usuario
```bash
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

```

2 . en app-routing-module.ts
```bash
  const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  { path: 'regist00ro', component: RegistroComponent },
  { path: 'lista', component: ListaProductoComponent, canActivate: [guard], data: { expectedRol: ['admin', 'user'] } },
  { path: 'detalle/:id', component: DetalleProductoComponent, canActivate: [guard], data: { expectedRol: ['admin', 'user'] } },
  { path: 'nuevo', component: NuevoProductoComponent, canActivate: [guard], data: { expectedRol: ['admin'] } },
  { path: 'editar/:id', component: EditarProductoComponent, canActivate: [guard], data: { expectedRol: ['admin'] } },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];
```
 #### En ListaProducto
```bash
 <app-menu></app-menu>
 <div class="container">
  <table class="table table-dark">
    <thead>
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Nombre</th>
        <th scope="col">Precio</th>
        <th scope="col">Ver</th>
        <th scope="col">Editar</th>
        <th scope="col">Borrar</th>
      </tr>
    </thead>
    <tbody>
      <tr *ngFor="let producto of productos">
        <td>{{producto.id}}</td>
        <td>{{producto.nombre}}</td>
        <td>{{producto.precio}}€</td>
        <td><button class="btn btn-outline-primary" routerLink="/detalle/{{producto.id}}"><i class="far fa-eye"></i>
            Ver</button></td>
        <td><button class="btn btn-outline-warning" routerLink="/editar/{{producto.id}}"><i class="far fa-edit"></i>
            Editar</button></td>
        <td><button class="btn btn-outline-danger" (click)="borrar(producto.id)"><i class="far fa-trash-alt"></i>
            Eliminar</button></td>
      </tr>
    </tbody>
  </table>

  <div class="mt-3">
    <button class="btn btn-outline-success btn-lg" routerLink="/nuevo">Nuevo Producto</button>
  </div>
  </div>
```
 #### En DetalleProducto

```bash
export class DetalleProductoComponent implements OnInit {

  producto: Producto = null;

  constructor(
    private productoService: ProductoService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;
    this.productoService.detail(id).subscribe(
      data => {
        this.producto = data;
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        this.volver();
      }
    );
  }

  volver(): void {
    this.router.navigate(['/lista']);
  }

}

```

### Lista Product 

1. Component.ts
```bash
  roles: string[];
  authority: string;
  isAdmin = false;


    ngOnInit() {
    this.cargarProductos();

    // Para token
    this.roles = this.tokenService.getAuthorities();
    this.roles.forEach(rol => {
      if(rol === 'ROLE_ADMIN') {
        this.isAdmin = true;
      }
    });
    }
```
2. Component.html
```bash
   <th scope="col" *ngIf="isAdmin">Editar</th>
        <th scope="col" *ngIf="isAdmin">Borrar</th>
      </tr>
    </thead>
    <tbody>
    .
    .
    .

        <td *ngIf="isAdmin"><button class="btn btn-outline-warning" routerLink="/editar/{{producto.id}}"><i class="far fa-edit"></i>
            Editar</button></td>
        <td *ngIf="isAdmin"><button class="btn btn-outline-danger" (click)="borrar(producto.id)"><i class="far fa-trash-alt"></i>
            Eliminar</button></td>
      </tr>
    </tbody>
  </table>

  <div class="mt-3" *ngIf="isAdmin">
    <button class="btn btn-outline-success btn-lg" routerLink="/nuevo">
      <i class="fas fa-plus-circle"></i> Nuevo Producto</button>
  </div>
</div>
```

  #### En aut/ registro.component

1. component.html

```bash
<app-menu></app-menu>
<div class="text-center" *ngIf="isLogged; else loggedOut">
  <h2>Ya has iniciado sesión</h2>
</div>

<ng-template #loggedOut>
  <div class="container d-flex justify-content-center">
    <div class="card">
      <div class="card-header">
        <ul class="nav nav-tabs">
          <li class="nav-item">
            <a class="nav-link" routerLink="/login">Iniciar Sesión</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" routerLink="/registro">Crear Cuenta</a>
          </li>

        </ul>
      </div>
      <div class="card-body">
        <form #f="ngForm" (ngSubmit)="onRegister()" novalidate>
          <div class="form-group">
            <label for="nombre">Nombre</label>
            <input type="text" name="nombre" id="nombre" class="form-control" [(ngModel)]="nombre" required>
          </div>
          <div class="form-group">
            <label for="nombreUsuario">Nombre de Usuario</label>
            <input type="text" name="nombreUsuario" id="nombreUsuario" class="form-control" [(ngModel)]="nombreUsuario"
              required>
          </div>
          <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" name="password" id="password" class="form-control" [(ngModel)]="password" required>
          </div>
          <div class="form-group">
            <label for="email">Correo Electrónico</label>
            <input type="text" name="email" id="email" class="form-control" [(ngModel)]="email" required>
          </div>
          <div class="form-group">
            <button class="btn btn-outline-success btn-block" [disabled]="!f.valid">
              <i class="fas fa-sign-in-alt"></i> Crear Cuenta</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</ng-template>
```
2. component.ts

```bash
export class RegistroComponent implements OnInit {

  nuevoUsuario: NuevoUsuario;
  nombre: string;
  nombreUsuario: string;
  email: string;
  password: string;
  errMsj: string;
  
  isLogged = false;

  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
    }
  }

  onRegister(): void {
    this.nuevoUsuario = new NuevoUsuario(this.nombre, this.nombreUsuario, this.email, this.password);
    this.authService.nuevo(this.nuevoUsuario).subscribe(
      data => {
        this.toastr.success('Cuenta Creada', 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });

       this.router.navigate(['/login']);
      },
      err => {
        this.errMsj = err.error.mensaje;
        this.toastr.error(this.errMsj, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        //console.log(err.error.message);
      }
    );
  }

}
```