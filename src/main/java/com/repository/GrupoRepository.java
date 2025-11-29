package com.repository;

import com.model.GrupoEstudio;
import com.dto.GrupoListadoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<GrupoEstudio, Long> {
    
    // ✅ AÑADE ESTA LÍNEA
    GrupoEstudio findByNombre(String nombre);

    @Query("SELECT new com.dto.GrupoListadoDTO(" +
           "g.id, g.nombre, m.nombre, g.horario, g.descripcion, " +
           "u.nombre, CAST(COUNT(DISTINCT gm.id) AS long)) " +
           "FROM GrupoEstudio g " +
           "LEFT JOIN g.materia m " +
           "LEFT JOIN g.creador u " +
           "LEFT JOIN g.miembros gm " +
           "GROUP BY g.id, g.nombre, m.nombre, g.horario, g.descripcion, u.nombre")
    List<GrupoListadoDTO> findAllGruposListado();
}
