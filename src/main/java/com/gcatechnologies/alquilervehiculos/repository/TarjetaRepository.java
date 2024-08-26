package com.gcatechnologies.alquilervehiculos.repository;

import com.gcatechnologies.alquilervehiculos.entities.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
    List<Tarjeta> findByUsuarioId(Long idUsuario);

}
