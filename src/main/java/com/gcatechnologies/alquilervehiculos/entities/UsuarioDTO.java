package com.gcatechnologies.alquilervehiculos.entities;

import java.util.List;

public class UsuarioDTO {

    private String nombre;
    private String nombrePila;
    private String apellido;
    private Long cedula;

    private String correo;

    private List<MedioPago> medioPagos;

    public UsuarioDTO(String nombre, String nombrePila, String apellido, Long cedula, String correo, List<MedioPago> medioPagos) {
        this.nombre = nombre;
        this.nombrePila = nombrePila;
        this.apellido = apellido;
        this.cedula = cedula;
        this.correo = correo;
        this.medioPagos = medioPagos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombrePila() {
        return nombrePila;
    }

    public void setNombrePila(String nombrePila) {
        this.nombrePila = nombrePila;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<MedioPago> getMedioPagos() {
        return medioPagos;
    }

    public void setMedioPagos(List<MedioPago> medioPagos) {
        this.medioPagos = medioPagos;
    }
}
