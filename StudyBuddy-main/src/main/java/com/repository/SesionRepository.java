package com.repository;

import com.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByGrupo_Id(Long grupoId);

    List<Sesion> findByUsuario_Id(Long usuarioId);
}
