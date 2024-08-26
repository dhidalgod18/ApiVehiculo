package com.gcatechnologies.alquilervehiculos.controller;
import com.gcatechnologies.alquilervehiculos.entities.MensajeDTO;
import com.gcatechnologies.alquilervehiculos.entities.Vehiculo;
import com.gcatechnologies.alquilervehiculos.service.VehiculoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vehiculo")
public class VehiculoController {
    private final VehiculoService vehiculoService;
    @Autowired
    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping("/nueva")
    public ResponseEntity<MensajeDTO> agregarVehiculo(@RequestBody Vehiculo vehiculo) {
        if (vehiculo.getPlaca().isEmpty() || vehiculo.getColor().isEmpty() || vehiculo.getModelo().isEmpty()
        || vehiculo.getMarca().isEmpty() || vehiculo.getCapacidad().isEmpty() || vehiculo.getValor() == null){
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos del vehículo son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
         vehiculoService.agregarVehiculo(vehiculo);

        MensajeDTO response = new MensajeDTO(
                "El vehiculo " + vehiculo.getPlaca() + " ha sido registrado exitosamente.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);

    }

    @PutMapping("/editar")
    public ResponseEntity<MensajeDTO> editarVehiculo(@RequestBody Vehiculo vehiculo) {

        if (vehiculo.getPlaca().isEmpty() || vehiculo.getColor().isEmpty() || vehiculo.getModelo().isEmpty()
                || vehiculo.getMarca().isEmpty() || vehiculo.getCapacidad().isEmpty() || vehiculo.getValor() == null
                || vehiculo.getIdVehiculo() == null) {
            MensajeDTO errorResponse = new MensajeDTO(
                    "Datos del vehículo son inválidos. Por favor, asegúrate de completar todos los campos necesarios.",
                    HttpStatus.BAD_REQUEST
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        vehiculoService.editarVehiculo(vehiculo);

        MensajeDTO response = new MensajeDTO(
                 " Se modifico exitosamente.",
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);

    }


    @DeleteMapping("/eliminar/{idVehiculo}")
    public ResponseEntity<MensajeDTO> eliminarVehiculo(@PathVariable int idVehiculo) {
        try {
            vehiculoService.eliminarVehiculo(idVehiculo);
            MensajeDTO response = new MensajeDTO(
                    "El vehículo ha sido eliminado exitosamente.",
                    HttpStatus.OK
            );
            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            MensajeDTO response = new MensajeDTO(
                    "No se pudo eliminar el vehículo: " + e.getMessage(),
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
