package com.gcatechnologies.alquilervehiculos.security.auth.filtros;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcatechnologies.alquilervehiculos.entities.Usuario;
import com.gcatechnologies.alquilervehiculos.security.auth.TokenJwtConfig;
import com.gcatechnologies.alquilervehiculos.security.validar.JwtAutenticarException;
import com.gcatechnologies.alquilervehiculos.service.impl.UsuarioPrincipal;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAutenticarLogin extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAutenticarLogin(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Usuario usuario;
        String correo;
        String contrasena;
        try {
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            correo = usuario.getCorreo();
            contrasena = usuario.getContrasena();

        } catch (IOException e) {
            throw new JwtAutenticarException("Error con los datos de autenticación", e);
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(correo,contrasena);
        logger.info("que tare este autenticacion (raw)"+authToken);

        Authentication authenticationResult = authenticationManager.authenticate(authToken);
        logger.info("Resultado de la autenticación: " + authenticationResult);
        return authenticationResult;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username = ((UsuarioPrincipal) authResult.getPrincipal()).getUsername();

        String token = Jwts.builder()
                .setSubject(username)
                .signWith(TokenJwtConfig.SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();

        response.addHeader(TokenJwtConfig.HEADER_AUTORIZATION, TokenJwtConfig.PREFIX_TOKEN + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", String.format("Hola %s, has iniciado sesion con exito!", username));
        body.put("username", username);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error en la autenticacion username o password incorrecto!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }

}

