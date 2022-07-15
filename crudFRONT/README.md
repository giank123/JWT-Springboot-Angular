# CrudFRONT

### Instalar
 ```bash
  npm install
  ng serve -o 
 ```

 ### Lo que habia - Productos
 . Editar
 . Lista
 . Detalle
 . Nuevo

## Lo que se creara

### EN LA CARPETA DE MODELOS
 ```bash
  ng g class nuevo-usuario --skipTests
  ng g class login-usuario --skipTests
  ng g class jwt-DTO --skipTests
 ```

 ### EN LA CARPETA DE SERVICIOS
 ```bash
  ng g s token --skipTests
  ng g s auth --skipTests
 ```

 ### Creamos una carpeta componentes
  ```bash
  ng g c auth/login --skipTests --flat
  ng g c auth/registro --skipTests --flat
 ```

  ### Creamos una carpeta interceptors
  ```bash
  ng g s interceptors/prod-interceptors --skipTests
 ```

### Creamos una carpeta guard
  ```bash
  ng g s guard/prod-guard --skipTests
 ```

### Creamos una carpeta menu
  ```bash
  ng g c menu --skipTests
 ```

 ### Creamos una carpeta index
  ```bash
  ng g c index --skipTests
 ```