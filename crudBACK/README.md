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
2. RefreshToken: http://localhost:8080/auth/refresh
Copiar el token anterior. para la generacion del nuevo token
 ```bash
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY1ODA5MzQ5MSwiZXhwIjoxNjU4MDkzNTExfQ.kqVAlwJQPqjLcCDw4UkQjEk6bzXCWi709N24r5EmrfjZKikWJJiSIVRMFkITCJpfgzXAOgUCvWYOnWiFpSuWOA"
}
```

## Products (ponemos el token del logueo en Authorization)
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
