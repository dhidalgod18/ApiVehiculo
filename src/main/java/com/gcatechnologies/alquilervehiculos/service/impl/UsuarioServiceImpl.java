package com.gcatechnologies.alquilervehiculos.service.impl;
import com.gcatechnologies.alquilervehiculos.entities.MedioPago;
import com.gcatechnologies.alquilervehiculos.entities.Usuario;
import com.gcatechnologies.alquilervehiculos.entities.UsuarioDTO;
import com.gcatechnologies.alquilervehiculos.repository.MedioPagoRepository;
import com.gcatechnologies.alquilervehiculos.repository.UsuarioRepository;
import com.gcatechnologies.alquilervehiculos.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final MedioPagoRepository medioPagoRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, MedioPagoRepository medioPagoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.medioPagoRepository = medioPagoRepository;
    }

    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        List<MedioPago> mediosDePagoDisponibles = medioPagoRepository.findAll();
        usuario.setMedioPagos(mediosDePagoDisponibles);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public boolean eliminarUsuario(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(idUsuario);
            return true;
        } else {
            return false;
        }


    }

    @Override
    public UsuarioDTO buscarUsuarioDTO(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            return new UsuarioDTO(u.getNombre(), u.getNombrePila(), u.getApellido(), u.getCedula(), u.getCorreo(), u.getMedioPagos());
        } else {
            throw new EntityNotFoundException("Usuario no encontrado.");
        }
    }

    @Override
    public Usuario buscarUsuario(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isPresent()){
            return usuario.get();
        }else {
            throw new EntityNotFoundException("Usuario no encontrado.");
        }
    }




}