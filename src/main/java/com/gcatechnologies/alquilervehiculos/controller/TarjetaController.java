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
import java.util.regex.Pattern;

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
        if (tarjeta.getMedioPago() == null ||tarjeta.getUsuario() == null || tarjeta.getNumeroTarjeta() == null || tarjeta.getCodigo() == null ||
                tarjeta.getFechaExpiracion() == null || tarjeta.getUsuario().getId() == null ||
                tarjeta.getMedioPago().getIdMedioPago() == null) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos de la tarjeta son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Pattern numericPattern = Pattern.compile("\\d+");
        if (!numericPattern.matcher(tarjeta.getNumeroTarjeta()).matches() ||
                !numericPattern.matcher(tarjeta.getCodigo()).matches()) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "El número de tarjeta y el código solo pueden contener dígitos.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String fechaExpiracion = tarjeta.getFechaExpiracion();
        Pattern fechaPattern = Pattern.compile("^(0[1-9]|1[0-2])/\\d{2}$");
        if (!fechaPattern.matcher(fechaExpiracion).matches()) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "El formato de la fecha de expiración debe ser MM/yy.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }


        Usuario usuario = usuarioService.buscarUsuario(tarjeta.getUsuario().getId());
        MedioPago medioPago = medioPagoService.buscarMedioId(tarjeta.getMedioPago().getIdMedioPago());

        if (medioPago.getNombre().equals("Tarjeta Debido") || medioPago.getNombre().equals("Tarjeta Credito")) {
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

            tarjetaService.agregarTarjeta(tarjeta1);
            MensajeDTO response = new MensajeDTO(
                    "La tarjeta ha sido agregada exitosamente.",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        }else {
            MensajeDTO response = new MensajeDTO(
                    "No cumple con el medio de pago correspondiente(Tarjeta Debito y/o Credito).",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        }
    }



    @GetMapping("/todos")
    public ResponseEntity<List<Tarjeta>> obtenerTarjeta() {
        List<Tarjeta> obtener = tarjetaService.obtenerTarjeta();
        return ResponseEntity.ok(obtener);
    }
    @GetMapping("/buscar/{idUsuario}")
    public ResponseEntity<List<TarjetaDTO>> obtenerTarjeta(@PathVariable Long idUsuario) {
        if (idUsuario == null ) {
            return ResponseEntity.badRequest().build();//400
        }
        try {
            List<TarjetaDTO> obtener = tarjetaService.buscarTarjetaDTO(idUsuario);
            if (obtener == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(obtener);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", "Tarjeta no encontrada para el usuario con ID.")
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/editar")
    public ResponseEntity<MensajeDTO> editarTarjeta(@RequestBody Tarjeta tarjeta) {
        if (tarjeta.getMedioPago() == null ||tarjeta.getUsuario() == null || tarjeta.getNumeroTarjeta() == null || tarjeta.getCodigo() == null ||
                tarjeta.getFechaExpiracion() == null || tarjeta.getUsuario().getId() == null ||
                tarjeta.getMedioPago().getIdMedioPago() == null) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos de la tarjeta son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Pattern numericPattern = Pattern.compile("\\d+");
        if (!numericPattern.matcher(tarjeta.getNumeroTarjeta()).matches() ||
                !numericPattern.matcher(tarjeta.getCodigo()).matches()) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "El número de tarjeta y el código solo pueden contener dígitos.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String fechaExpiracion = tarjeta.getFechaExpiracion();
        Pattern fechaPattern = Pattern.compile("^(0[1-9]|1[0-2])/\\d{2}$");
        if (!fechaPattern.matcher(fechaExpiracion).matches()) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "El formato de la fecha de expiración debe ser MM/yy.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }


        Usuario usuario = usuarioService.buscarUsuario(tarjeta.getUsuario().getId());
        MedioPago medioPago = medioPagoService.buscarMedioId(tarjeta.getMedioPago().getIdMedioPago());

        if (medioPago.getNombre().equals("Tarjeta Debido") || medioPago.getNombre().equals("Tarjeta Credito")) {
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
                    "La tarjeta ha sido agregada exitosamente.",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        }else {
            MensajeDTO response = new MensajeDTO(
                    "No cumple con el medio de pago correspondiente(Tarjeta Debito y/o Credito).",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);
        }
    }


        @DeleteMapping("/eliminar/{idTarjeta}")
    public ResponseEntity<MensajeDTO> eliminarTarjeta(@PathVariable Long idTarjeta) {
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
