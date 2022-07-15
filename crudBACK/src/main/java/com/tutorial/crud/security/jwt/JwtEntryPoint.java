package com.tutorial.crud.security.jwt;

//Comprobar si hay un token valido si no devuelve una respuesta 401 , no autorizado

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    //Metodo que da error en caso no funcione la aplicacion.
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    //Rechaza todas las peticiones no autenticadas
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        logger.error("fail en el método commence");
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no autorizado");
    }
}
