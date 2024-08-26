package com.gcatechnologies.alquilervehiculos.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeDTO {
    private String mensaje;
    private HttpStatus estado;
}
