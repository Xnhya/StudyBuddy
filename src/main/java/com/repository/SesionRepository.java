package com.repository;

import com.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.dto.SesionListadoDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByGrupo_Id(Long grupoId);

    List<Sesion> findByUsuario_Id(Long usuarioId);

    @Query("SELECT new com.dto.SesionListadoDTO(s.id, s.tema, s.fecha, s.hora, g.nombre, u.nombre) " +
           "FROM Sesion s " +
           "LEFT JOIN s.grupo g " +
           "LEFT JOIN s.usuario u")
    List<SesionListadoDTO> findAllSesionesListado();
}
