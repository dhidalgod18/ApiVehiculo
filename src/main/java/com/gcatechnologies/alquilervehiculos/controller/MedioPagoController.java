package com.gcatechnologies.alquilervehiculos.controller;
import com.gcatechnologies.alquilervehiculos.entities.MedioPago;
import com.gcatechnologies.alquilervehiculos.entities.MensajeDTO;
import com.gcatechnologies.alquilervehiculos.service.MedioPagoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pago")
public class MedioPagoController {
    private final MedioPagoService medioPagoService;
    @Autowired
    public MedioPagoController(MedioPagoService medioPagoService) {
        this.medioPagoService = medioPagoService;
    }

    @PostMapping("/nueva")
    public ResponseEntity<MensajeDTO> agregarMedioPago(@RequestBody MedioPago medioPago) {
        if (medioPago.getNombre() == null || medioPago.getDescripcion() == null){
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos del vehículo son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        medioPagoService.crearMedioPago(medioPago);

        MensajeDTO response = new MensajeDTO(
                "El medio de pago " + medioPago.getNombre() + " ha sido registrado exitosamente.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);

    }

    @PutMapping("/editar")
    public ResponseEntity<MensajeDTO> editarMedioPago(@RequestBody MedioPago medioPago) {

        if (medioPago.getIdMedioPago() == null ||
                medioPago.getNombre() == null || medioPago.getDescripcion() == null) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos del Medio de pago son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        medioPagoService.editarMedioPago(medioPago);

        MensajeDTO response = new MensajeDTO(
                " Se modifico exitosamente.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);

    }


    @DeleteMapping("/eliminar/{idMedio}")
    public ResponseEntity<MensajeDTO> eliminarVehiculo(@PathVariable int idMedio) {
        try {
            medioPagoService.eliminarMedioPago(idMedio);
            MensajeDTO response = new MensajeDTO(
                    "El medio de pago ha sido eliminado exitosamente.",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            MensajeDTO response = new MensajeDTO(
                    "No se pudo eliminar el medio de pago: " + e.getMessage(),
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

    @GetMapping("/todos")
    public ResponseEntity<List<MedioPago>> obtenerMediosPago() {
        List<MedioPago> obtener = medioPagoService.obtenerMetodosPago();
        return ResponseEntity.ok(obtener);
    }


}
