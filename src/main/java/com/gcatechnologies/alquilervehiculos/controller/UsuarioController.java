package com.gcatechnologies.alquilervehiculos.controller;
import com.gcatechnologies.alquilervehiculos.entities.MensajeDTO;
import com.gcatechnologies.alquilervehiculos.entities.Usuario;
import com.gcatechnologies.alquilervehiculos.entities.UsuarioDTO;
import com.gcatechnologies.alquilervehiculos.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("usuarios")
public class UsuarioController {


    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/nueva")
    public ResponseEntity<MensajeDTO> agregarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombrePila() == null || usuario.getApellido() == null
                || usuario.getCedula() == null || usuario.getContrasena() == null){
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos del usuario son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        String pass = usuario.getContrasena();
        String codificada = passwordEncoder.encode(pass);
        usuario.setContrasena(codificada);
        usuario.setRol("ROLE_USER");
        usuarioService.agregarUsuario(usuario);
        MensajeDTO response = new MensajeDTO(
                "El usuario " + usuario.getNombre() + " ha sido registrado exitosamente.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);

    }

    @GetMapping("/todos")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> obtener = usuarioService.obtenerUsuarios();
        return ResponseEntity.ok(obtener);
    }

    @GetMapping("/buscar/{idUsuario}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long idUsuario) {
        if (idUsuario == null ) {
            return ResponseEntity.badRequest().build();//400
        }
        try {
            UsuarioDTO usuario = usuarioService.buscarUsuarioDTO(idUsuario);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(usuario);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar/{idUsuario}")
    public ResponseEntity<MensajeDTO> eliminarUsuario(@PathVariable Long idUsuario) {
        try {
            usuarioService.eliminarUsuario(idUsuario);
            MensajeDTO response = new MensajeDTO(
                    "El usuario ha sido eliminado exitosamente.",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            MensajeDTO response = new MensajeDTO(
                    "No se pudo eliminar el usuario: " + e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {
            MensajeDTO response = new MensajeDTO(
                    "Ocurrió un error inesperado: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
