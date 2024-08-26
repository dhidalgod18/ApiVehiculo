package com.gcatechnologies.alquilervehiculos.security.config;

import com.gcatechnologies.alquilervehiculos.security.auth.filtros.JwtAutenticarLogin;
import com.gcatechnologies.alquilervehiculos.security.auth.filtros.JwtValidarToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration


public class SeguridadPrincipal {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public SeguridadPrincipal(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/usuarios/nueva").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios/buscar/{idUsuario}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/eliminar/{idUsuario}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios/todos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/alquilar/nuevo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/vehiculo/nueva").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/vehiculo/eliminar/{idVehiculo}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/vehiculo/editar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pago/nueva").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/pago/eliminar/{idMedio}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/pago/editar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pago/todos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/tarjeta/nueva").permitAll()
                        .requestMatchers(HttpMethod.GET, "/tarjeta/todos").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/tarjeta/eliminar/{idTarjeta}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/alquiler/nueva").permitAll()
                        .requestMatchers(HttpMethod.GET, "/alquiler/todos").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/alquiler/cambiarEstado").permitAll()
                        .requestMatchers(HttpMethod.GET, "/tarjeta/buscar/{idUsuario}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/tarjeta/editar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/alquiler/buscar/{idalquiler}").permitAll()








                        .anyRequest().authenticated())
                .addFilter(new JwtAutenticarLogin(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidarToken(authenticationConfiguration.getAuthenticationManager()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
