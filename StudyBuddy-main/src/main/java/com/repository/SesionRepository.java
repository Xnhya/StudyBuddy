package com.repository;

import com.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Integer> {
    // Por ahora no necesitamos métodos extra, JpaRepository
    // ya nos da .save(), .findAll(), .findById(), .deleteById(), etc.
}