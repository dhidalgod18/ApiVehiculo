package com.gcatechnologies.alquilervehiculos.entities;

public class TarjetaDTO {
    private String nombreUsuario;
    private String nombreMedioPago;
    private String numeroTarjeta;

    public TarjetaDTO(String nombreUsuario, String nombreMedioPago, String numeroTarjeta) {
        this.nombreUsuario = nombreUsuario;
        this.nombreMedioPago = nombreMedioPago;
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreMedioPago() {
        return nombreMedioPago;
    }

    public void setNombreMedioPago(String nombreMedioPago) {
        this.nombreMedioPago = nombreMedioPago;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
}
