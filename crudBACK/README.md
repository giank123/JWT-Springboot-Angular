# JWT  - JAVA - ANGULAR 
## Giancarlos Perez Malca [Linkedn](https://www.linkedin.com/in/giancarlosperez/)

1. Maven
```bash
 Spring Security:  https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
 Json Web Token: https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt 
```

2. Application
```bash
 jwt.secret = secret
 jwt.expiration = 3600
```
### Postman
## Usuario
1. Nuevo_Admin: http://localhost:8080/auth/nuevo
 ```bash
 {
    "nombre": "admin",
    "nombreUsuario": "admin",
    "email": "a@a.a",
    "password":"admin",
    "roles": ["admin"]
 }
```
2. Nuevo_Usuario: http://localhost:8080/auth/nuevo
 ```bash
{
  "nombre": "user",
  "nombreUsuario": "user",
  "email": "u@u.u",
  "password":"user"
}
```

## Login
1. Login: http://localhost:8080/auth/login
 ```bash
{
    "nombreUsuario": "user",
    "password":"user"
}
```

## Products
1. Create: http://localhost:8080/producto/create
 ```bash
  {
    "nombre": "Pie",
    "precio": 61
  }
```
2. Update: http://localhost:8080/producto/update/1
 ```bash
  {
    "nombre": "Pie",
    "precio": 60
  }
```
3. Listar: http://localhost:8080/producto/lista
4. Delete By Id:  http://localhost:8080/producto/delete/2
