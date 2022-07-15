package com.tutorial.crud.security.jwt;

// Mas importante , se va a ejecutar por cada peticion.
//Comprobar que sea validado el token, utilizando el provider
//si es valido, permitira acceso al recurso.


import com.tutorial.crud.security.service.UserDetailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Se va a ejecutar una vez por cada peticion
public class JwtTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailServiceImpl userDetailService;


    //Comprobar que el token sea valido.
    //Si esta bien permite el acceso al recurso
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getToken(req);
            //comprobamos que el token sea valido
            if(token != null && jwtProvider.validateToken(token)){
                // Nombre del usuario
                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                // Datos del usuario que obtendremos del token.
                UserDetails userDetails = userDetailService.loadUserByUsername(nombreUsuario);
                // Creamos una autenticacion
                // autoridades que tiene ademas de la autentificacion necesitamos la autorizacion .para que pueda acceder a los recursos.
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //Al contexto de autentificacion le asignamos ese usuario.

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e){
            logger.error("fallo en el metodo doFilter "+e.getMessage());
        }

        filterChain.doFilter(req, res);

    }

    //metodo privado para extraer el token y eliminar el "Bearer + "
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }

}
