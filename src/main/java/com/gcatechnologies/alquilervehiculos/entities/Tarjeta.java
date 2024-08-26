package com.gcatechnologies.alquilervehiculos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarjeta;
    private String numeroTarjeta;

    private String fechaExpiracion;

    private String codigo;

    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private MedioPago medioPago;
}
