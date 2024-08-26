package com.gcatechnologies.alquilervehiculos.repository;
import com.gcatechnologies.alquilervehiculos.entities.Alquilar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilarRepository extends JpaRepository<Alquilar, Long> {
}
