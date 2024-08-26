package com.gcatechnologies.alquilervehiculos.service.impl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
@AllArgsConstructor

public class UsuarioPrincipal implements UserDetails {
    private String correo;
    private String rol;
    private String contrasena;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority((rol));
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return contrasena;
    }


    @Override
    public String getUsername() {
        return correo;
    }
}
