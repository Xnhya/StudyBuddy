package com.repository;


import com.model.GrupoEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
// Apunta a tu nueva entidad 'GrupoEstudio' y su ID 'Long'
public interface GrupoEstudioRepository extends JpaRepository<GrupoEstudio, Integer> { 
    // Aquí pondremos tus métodos de DTO más tarde

    @Query("select new com.dto.GrupoListadoDTO(g.id, g.nombre, m.nombreMateria, u.nombre, u.id) " +
           "from GrupoEstudio g " +
           "join g.materia m " +
           "join g.creador u")
    List<com.dto.GrupoListadoDTO> findGruposConDetalles();
}
