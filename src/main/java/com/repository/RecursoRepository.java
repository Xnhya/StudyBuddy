package com.repository;

import com.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    List<Recurso> findByGrupo_Id(Long grupoId);
    List<Recurso> findByAutor(String autor);
    List<Recurso> findByTipo(String tipo);
}

