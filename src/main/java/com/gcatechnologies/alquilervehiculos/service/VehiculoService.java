package com.gcatechnologies.alquilervehiculos.service;

import com.gcatechnologies.alquilervehiculos.entities.Vehiculo;
import org.springframework.stereotype.Service;

@Service
public interface VehiculoService {

    Vehiculo agregarVehiculo(Vehiculo vehiculo);

    boolean editarVehiculo(Vehiculo vehiculo);

    boolean eliminarVehiculo(int idVehiculo);
    Vehiculo buscarVehiculo(Long idVehiculo);

}
