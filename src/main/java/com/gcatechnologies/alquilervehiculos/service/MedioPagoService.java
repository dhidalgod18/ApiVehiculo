package com.gcatechnologies.alquilervehiculos.service;
import com.gcatechnologies.alquilervehiculos.entities.MedioPago;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedioPagoService {

    MedioPago crearMedioPago(MedioPago medioPago);
    boolean editarMedioPago(MedioPago medioPago);

    boolean eliminarMedioPago(int idMedioPago);

    List<MedioPago> obtenerMetodosPago();

    MedioPago buscarMedioId(Long idMedio);



}
