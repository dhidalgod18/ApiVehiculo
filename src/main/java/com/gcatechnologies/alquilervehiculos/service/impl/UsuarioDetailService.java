package com.gcatechnologies.alquilervehiculos.service.impl;

import com.gcatechnologies.alquilervehiculos.entities.Usuario;
import com.gcatechnologies.alquilervehiculos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailService implements UserDetailsService {

    private final  UsuarioRepository usuarioRepository;
    @Autowired
    public UsuarioDetailService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findcorreo(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new UsuarioPrincipal(user.getCorreo(),user.getRol(),user.getContrasena());
    }
}
