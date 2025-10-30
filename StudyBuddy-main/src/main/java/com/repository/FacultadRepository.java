package com.repository;

import com.model.Facultad; // Apunta a tu modelo Facultad.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Le decimos a Spring que maneje la entidad 'Facultad', cuyo ID es 'Integer'
public interface FacultadRepository extends JpaRepository<Facultad, Integer> {
    
    // No necesitas escribir nada aquí.
    // Ya tienes .findAll(), .findById(), etc., gratis.
    // Lo usaremos para llenar la primera lista desplegable (dropdown).
}