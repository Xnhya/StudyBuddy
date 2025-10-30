package com.repository;


import com.model.GrupoEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Apunta a tu nueva entidad 'GrupoEstudio' y su ID 'Long'
public interface GrupoEstudioRepository extends JpaRepository<GrupoEstudio, Integer> { 
    // Aquí pondremos tus métodos de DTO más tarde
}
