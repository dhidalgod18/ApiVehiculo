package com.gcatechnologies.alquilervehiculos.service;

import com.gcatechnologies.alquilervehiculos.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TarjetaService {
    Tarjeta agregarTarjeta(Tarjeta tarjeta);
    List<Tarjeta> obtenerTarjeta();

    boolean eliminarTarjeta(int idTarjeta);

    boolean editarTarjeta(Tarjeta tarjeta);

    TarjetaDTO buscarTarjetaDTO(Long idUsuario);

}
