package com.gcatechnologies.alquilervehiculos.repository;

import com.gcatechnologies.alquilervehiculos.entities.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
    Optional<Tarjeta> findByUsuarioId(Long idUsuario);

}
