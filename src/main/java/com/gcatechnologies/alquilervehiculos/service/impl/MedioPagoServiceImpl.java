package com.gcatechnologies.alquilervehiculos.service.impl;
import com.gcatechnologies.alquilervehiculos.entities.MedioPago;
import com.gcatechnologies.alquilervehiculos.entities.Usuario;
import com.gcatechnologies.alquilervehiculos.repository.MedioPagoRepository;
import com.gcatechnologies.alquilervehiculos.repository.UsuarioRepository;
import com.gcatechnologies.alquilervehiculos.service.MedioPagoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedioPagoServiceImpl implements MedioPagoService {

    private final MedioPagoRepository medioPagoRepository;

    private final UsuarioRepository usuarioRepository;


    @Autowired
    public MedioPagoServiceImpl(MedioPagoRepository medioPagoRepository, UsuarioRepository usuarioRepository) {
        this.medioPagoRepository = medioPagoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public MedioPago crearMedioPago(MedioPago medioPago) {
        MedioPago nuevoMedioPago = medioPagoRepository.save(medioPago);
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            List<MedioPago> medioPagos = usuario.getMedioPagos();
            if (medioPagos == null) {
                medioPagos = new ArrayList<>();
            }
            medioPagos.add(nuevoMedioPago);
            usuario.setMedioPagos(medioPagos);
            usuarioRepository.save(usuario);
        }

        return nuevoMedioPago;
    }



    @Override
    public boolean editarMedioPago(MedioPago medioPago) {
        Optional<MedioPago> medioPago1 = medioPagoRepository.findById(medioPago.getIdMedioPago());
        if (medioPago1.isPresent()) {
            MedioPago medioPagoModificado = medioPago1.get();
            medioPagoModificado.setNombre(medioPago.getNombre());
            medioPagoModificado.setDescripcion(medioPago.getDescripcion());
            medioPagoRepository.save(medioPagoModificado);
            return true;
        } else {
            throw new EntityNotFoundException("Medio de pago no encontrado.");

        }

    }

    @Override
    public boolean eliminarMedioPago(int idMedioPago) {
            Optional<MedioPago> medioPago = medioPagoRepository.findById((long) idMedioPago);
            if (medioPago.isPresent()){
                medioPagoRepository.deleteById((long) idMedioPago);
                return true;
            }else {
                return false;
            }

    }


    @Override
    public List<MedioPago> obtenerMetodosPago() {
        return medioPagoRepository.findAll();
    }

    @Override
    public MedioPago buscarMedioId(Long idMedio) {
        Optional<MedioPago> medioPago = medioPagoRepository.findById(idMedio);
        if (medioPago.isPresent()){
            return medioPago.get();
        }else {
            throw new EntityNotFoundException("Medio no encontrado.");
        }
    }


}
