package com.gcatechnologies.alquilervehiculos.controller;

import com.gcatechnologies.alquilervehiculos.entities.*;
import com.gcatechnologies.alquilervehiculos.service.MedioPagoService;
import com.gcatechnologies.alquilervehiculos.service.TarjetaService;
import com.gcatechnologies.alquilervehiculos.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("tarjeta")
public class TarjetaController {

    private final TarjetaService tarjetaService;
    private final UsuarioService usuarioService;

    private final MedioPagoService medioPagoService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TarjetaController(TarjetaService tarjetaService, UsuarioService usuarioService, MedioPagoService medioPagoService, PasswordEncoder passwordEncoder) {
        this.tarjetaService = tarjetaService;
        this.usuarioService = usuarioService;
        this.medioPagoService = medioPagoService;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/nueva")
    public ResponseEntity<MensajeDTO> agregarTarjeta(@RequestBody Tarjeta tarjeta) {
        if (tarjeta.getNumeroTarjeta() == null || tarjeta.getCodigo() == null ||
                tarjeta.getFechaExpiracion() == null || tarjeta.getUsuario().getId() == null ||
                tarjeta.getMedioPago().getIdMedioPago() == null) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos de la tarjeta son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Usuario usuario = usuarioService.buscarUsuario(tarjeta.getUsuario().getId());
        MedioPago medioPago = medioPagoService.buscarMedioId(tarjeta.getMedioPago().getIdMedioPago());

        Tarjeta tarjeta1 = new Tarjeta();
        String tarjetaEntera = tarjeta.getNumeroTarjeta();
        String tarjetaCodificada = passwordEncoder.encode(tarjetaEntera);
        tarjeta1.setNumeroTarjeta(tarjetaCodificada);
        tarjeta1.setFechaExpiracion(tarjeta.getFechaExpiracion());

        String codigo = tarjeta.getCodigo();
        String codigoOculto = passwordEncoder.encode(codigo);
        tarjeta1.setCodigo(codigoOculto);
        tarjeta1.setUsuario(usuario);
        tarjeta1.setMedioPago(medioPago);

        tarjetaService.agregarTarjeta(tarjeta1);

        MensajeDTO response = new MensajeDTO(
                "La tarjeta ha sido registrada exitosamente.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/todos")
    public ResponseEntity<List<Tarjeta>> obtenerTarjeta() {
        List<Tarjeta> obtener = tarjetaService.obtenerTarjeta();
        return ResponseEntity.ok(obtener);
    }
    @GetMapping("/buscar/{idUsuario}")
    public ResponseEntity<TarjetaDTO> obtenerTarjeta(@PathVariable Long idUsuario) {
        if (idUsuario == null ) {
            return ResponseEntity.badRequest().build();//400
        }
        try {
            TarjetaDTO tarjetaDTO = tarjetaService.buscarTarjetaDTO(idUsuario);
            if (tarjetaDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(tarjetaDTO);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/editar")
    public ResponseEntity<MensajeDTO> editarTarjeta(@RequestBody Tarjeta tarjeta) {

        if (tarjeta.getIdTarjeta() == null || tarjeta.getNumeroTarjeta() == null || tarjeta.getCodigo() == null ||
                tarjeta.getFechaExpiracion() == null || tarjeta.getUsuario().getId() == null ||
                tarjeta.getMedioPago().getIdMedioPago() == null) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos de la tarjeta son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Usuario usuario = usuarioService.buscarUsuario(tarjeta.getUsuario().getId());
        MedioPago medioPago = medioPagoService.buscarMedioId(tarjeta.getMedioPago().getIdMedioPago());

        Tarjeta tarjeta1 = new Tarjeta();
        String tarjetaEntera = tarjeta.getNumeroTarjeta();
        String tarjetaCodificada = passwordEncoder.encode(tarjetaEntera);
        tarjeta1.setNumeroTarjeta(tarjetaCodificada);
        tarjeta1.setFechaExpiracion(tarjeta.getFechaExpiracion());

        String codigo = tarjeta.getCodigo();
        String codigoOculto = passwordEncoder.encode(codigo);
        tarjeta1.setCodigo(codigoOculto);
        tarjeta1.setUsuario(usuario);
        tarjeta1.setMedioPago(medioPago);
        tarjeta1.setIdTarjeta(tarjeta.getIdTarjeta());

        tarjetaService.editarTarjeta(tarjeta1);
        MensajeDTO response = new MensajeDTO(
                "La tarjeta ha sido modificada exitosamente.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }



        @DeleteMapping("/eliminar/{idTarjeta}")
    public ResponseEntity<MensajeDTO> eliminarTarjeta(@PathVariable int idTarjeta) {
        try {
            tarjetaService.eliminarTarjeta(idTarjeta);
            MensajeDTO response = new MensajeDTO(
                    "La tarjeta ha sido eliminado exitosamente.",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            MensajeDTO response = new MensajeDTO(
                    "No se pudo eliminar la tarjeta: " + e.getMessage(),
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
