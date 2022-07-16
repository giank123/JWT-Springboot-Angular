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