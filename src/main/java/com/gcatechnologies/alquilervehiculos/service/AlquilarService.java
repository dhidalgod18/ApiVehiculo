package com.gcatechnologies.alquilervehiculos.service;

import com.gcatechnologies.alquilervehiculos.entities.Alquilar;
import com.gcatechnologies.alquilervehiculos.entities.AlquilerDTO;
import com.gcatechnologies.alquilervehiculos.entities.EstadoDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlquilarService {

    Alquilar crearRenta(Alquilar alquilar);

    List<Alquilar> obtenerRentas();

    boolean cambiarEstado(EstadoDAO estadoDAO);

    AlquilerDTO buscarAlquilar(Long idAlquilar);






}
