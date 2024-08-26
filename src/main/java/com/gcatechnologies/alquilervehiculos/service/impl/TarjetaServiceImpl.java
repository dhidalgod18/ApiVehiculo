package com.gcatechnologies.alquilervehiculos.service.impl;

import com.gcatechnologies.alquilervehiculos.entities.Tarjeta;
import com.gcatechnologies.alquilervehiculos.entities.TarjetaDTO;
import com.gcatechnologies.alquilervehiculos.repository.TarjetaRepository;
import com.gcatechnologies.alquilervehiculos.service.TarjetaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarjetaServiceImpl implements TarjetaService {
    private final TarjetaRepository tarjetaRepository;
    @Autowired
    public TarjetaServiceImpl(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    @Override
    public Tarjeta agregarTarjeta(Tarjeta tarjeta) {
        return tarjetaRepository.save(tarjeta);
    }

    @Override
    public List<Tarjeta> obtenerTarjeta() {
        return tarjetaRepository.findAll();
    }

    @Override
    public boolean eliminarTarjeta(int idTarjeta) {
        Optional<Tarjeta> tarjeta = tarjetaRepository.findById((long) idTarjeta);
        if (tarjeta.isPresent()){
            tarjetaRepository.deleteById((long) idTarjeta);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean editarTarjeta(Tarjeta tarjeta) {
        Optional<Tarjeta> tarjeta1 = tarjetaRepository.findById(tarjeta.getIdTarjeta());
        if (tarjeta1.isPresent()) {
            Tarjeta tarjetaModificada = tarjeta1.get();
            tarjetaModificada.setNumeroTarjeta(tarjeta.getNumeroTarjeta());
            tarjetaModificada.setFechaExpiracion(tarjeta.getFechaExpiracion());
            tarjetaModificada.setCodigo(tarjeta.getCodigo());
            tarjetaRepository.save(tarjetaModificada);
            return true;
        } else {
            throw new EntityNotFoundException(" no se encontro.");

        }

    }

    @Override
    public TarjetaDTO buscarTarjetaDTO(Long idUsuario) {
        Optional<Tarjeta> tarjetaOptional = tarjetaRepository.findByUsuarioId(idUsuario);
        if (tarjetaOptional.isPresent()) {
            Tarjeta tarjeta = tarjetaOptional.get();
            return new TarjetaDTO(
                    tarjeta.getUsuario().getNombre(),
                    tarjeta.getMedioPago().getNombre(),
                    tarjeta.getNumeroTarjeta()
            );
        } else {
            throw new EntityNotFoundException("Tarjeta no encontrada para el usuario con ID: " + idUsuario);
        }
    }

}
