package com.gcatechnologies.alquilervehiculos.repository;
import com.gcatechnologies.alquilervehiculos.entities.MedioPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedioPagoRepository extends JpaRepository<MedioPago, Long> {
}
