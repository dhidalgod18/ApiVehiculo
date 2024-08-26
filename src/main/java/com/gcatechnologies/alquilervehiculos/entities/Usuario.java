package com.gcatechnologies.alquilervehiculos.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String nombrePila;
    private String apellido;
    private Long cedula;

    private String correo;

    private String contrasena;

    private String rol;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    fetch = FetchType.EAGER)
    @JoinTable( name = "usuario_medio_pagos",
            joinColumns = @JoinColumn(
                    name = "usuario_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "Medio_pago_id",
                    referencedColumnName = "idMedioPago"
            )


    )
    private List<MedioPago> medioPagos;





}
