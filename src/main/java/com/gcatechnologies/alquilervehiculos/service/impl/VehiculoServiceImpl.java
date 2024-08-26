package com.gcatechnologies.alquilervehiculos.service.impl;

import com.gcatechnologies.alquilervehiculos.entities.Vehiculo;
import com.gcatechnologies.alquilervehiculos.repository.VehiculoRepository;
import com.gcatechnologies.alquilervehiculos.service.VehiculoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiculoServiceImpl  implements VehiculoService {
    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public Vehiculo agregarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }


    @Override
    public boolean editarVehiculo(Vehiculo vehiculo) {
        Optional<Vehiculo> vehiculo1 = vehiculoRepository.findById(vehiculo.getIdVehiculo());
        if (vehiculo1.isPresent()) {
            Vehiculo vehiculoModificado = vehiculo1.get();
            vehiculoModificado.setCapacidad(vehiculo.getCapacidad());
            vehiculoModificado.setColor(vehiculo.getColor());
            vehiculoModificado.setMarca(vehiculo.getMarca());
            vehiculoModificado.setModelo(vehiculo.getModelo());
            vehiculoModificado.setPlaca(vehiculo.getPlaca());
            vehiculoModificado.setValor(vehiculo.getValor());
            vehiculoRepository.save(vehiculoModificado);
            return true;
        } else {
            throw new EntityNotFoundException("Vehículo con ID " + vehiculo.getIdVehiculo()+ " no encontrado.");

        }

    }
    @Override
    public boolean eliminarVehiculo(int idVehiculo) {
    Optional<Vehiculo> vehiculo = vehiculoRepository.findById((long) idVehiculo);
    if (vehiculo.isPresent()){
        vehiculoRepository.deleteById((long) idVehiculo);
        return true;
    }else {
        return false;
    }

    }

    @Override
    public Vehiculo buscarVehiculo(Long idVehiculo) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(idVehiculo);
        if (vehiculo.isPresent()){
            return vehiculo.get();
        }else {
            throw new EntityNotFoundException("Vehiculo no encontrado.");
        }
    }
}