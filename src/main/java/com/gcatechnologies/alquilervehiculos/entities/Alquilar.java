package com.gcatechnologies.alquilervehiculos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alquilar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlquilar;
    private LocalDateTime fechaInicial;

    private LocalDateTime fechafinal;

    private Long valor;
    @ManyToOne
    private Vehiculo vehiculo;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private MedioPago medioPago;

    private String estado;






}
