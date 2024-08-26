package com.gcatechnologies.alquilervehiculos.controller;
import com.gcatechnologies.alquilervehiculos.entities.*;
import com.gcatechnologies.alquilervehiculos.service.AlquilarService;
import com.gcatechnologies.alquilervehiculos.service.MedioPagoService;
import com.gcatechnologies.alquilervehiculos.service.UsuarioService;
import com.gcatechnologies.alquilervehiculos.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("alquiler")
public class AlquilerController {

    private final AlquilarService alquilarService;
    private final UsuarioService usuarioService;

    private final MedioPagoService medioPagoService;

    private final VehiculoService vehiculoService;
    @Autowired
    public AlquilerController(AlquilarService alquilarService, UsuarioService usuarioService, MedioPagoService medioPagoService, VehiculoService vehiculoService) {
        this.alquilarService = alquilarService;
        this.usuarioService = usuarioService;
        this.medioPagoService = medioPagoService;
        this.vehiculoService = vehiculoService;
    }

    @PostMapping("/nueva")
    public ResponseEntity<MensajeDTO> agregarAlquiler(@RequestBody Alquilar alquilar) {
        if (alquilar.getFechaInicial() == null || alquilar.getFechafinal() == null || alquilar.getMedioPago().getIdMedioPago() == null
                || alquilar.getUsuario().getId() == null || alquilar.getVehiculo().getIdVehiculo() == null || alquilar.getEstado() == null) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos del alquiler son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Usuario usuario = usuarioService.buscarUsuario(alquilar.getUsuario().getId());
        MedioPago medioPago = medioPagoService.buscarMedioId(alquilar.getMedioPago().getIdMedioPago());
        Vehiculo vehiculo = vehiculoService.buscarVehiculo(alquilar.getVehiculo().getIdVehiculo());

        Alquilar alquilar1 = new Alquilar();
        alquilar1.setFechaInicial(alquilar.getFechaInicial());
        alquilar1.setFechafinal(alquilar.getFechafinal());
        alquilar1.setEstado(alquilar.getEstado());
        alquilar1.setVehiculo(vehiculo);
        alquilar1.setMedioPago(medioPago);
        alquilar1.setUsuario(usuario);

        LocalDateTime fechaInicial = alquilar.getFechaInicial();
        LocalDateTime fechaFinal = alquilar.getFechafinal();
        long numeroHoras = ChronoUnit.HOURS.between(fechaInicial, fechaFinal);

        // Calcular el valor del alquiler por hora
        Long valorPorHora = vehiculo.getValor(); // Suponiendo que 'valor' es el costo por hora
        Long valorTotal = numeroHoras * valorPorHora;
        alquilar1.setValor(valorTotal);


        alquilarService.crearRenta(alquilar1);

        MensajeDTO response = new MensajeDTO(
                "El alquiler con placa del vehiculo " + vehiculo.getPlaca() + " ha sido registrado exitosamente.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);

    }

    @GetMapping("/todos")
    public ResponseEntity<List<Alquilar>> obtenerAlquiler() {
        List<Alquilar> obtener = alquilarService.obtenerRentas();
        return ResponseEntity.ok(obtener);
    }
    @PutMapping("/cambiarEstado")
    public ResponseEntity<MensajeDTO> cambiarEstado(@RequestBody EstadoDAO estadoDAO) {

        if (estadoDAO.getIdAlquiler() == null ||
                estadoDAO.getEstado() == null ) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos cambiar estado son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if (estadoDAO.getEstado().equals("Abierto") || estadoDAO.getEstado().equals("Cerrado")
                || estadoDAO.getEstado().equals("En revision")){
            estadoDAO.setEstado(estadoDAO.getEstado());
        }else {
            MensajeDTO errorResponse = new MensajeDTO(
                    "Estado no existe.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        alquilarService.cambiarEstado(estadoDAO);

        MensajeDTO response = new MensajeDTO(
                " Se modifico exitosamente.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);

    }

    @GetMapping("/buscar/{idalquiler}")
    public ResponseEntity<AlquilerDTO> obtenerRenta(@PathVariable Long idalquiler) {
        if (idalquiler == null ) {
            return ResponseEntity.badRequest().build();//400
        }
        try {
            AlquilerDTO alquilerDTO = alquilarService.buscarAlquilar(idalquiler);
            if (alquilerDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(alquilerDTO);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
