package com.tutorial.crud.security.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//Utilizar en el momento de hacer un login , responseEntity del controlador
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {
    private String token;

//    public JwtDto(String token) {
//        this.token = token;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
