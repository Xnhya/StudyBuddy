package com.repository;

import com.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Integer> {

    // Spring Data JPA crea la consulta: "SELECT * FROM Recurso WHERE id_grupo = ?"
    List<Recurso> findByGrupo_Id(Integer grupoId);

    // Spring Data JPA crea la consulta: "SELECT * FROM Recurso WHERE autor = ?"
    List<Recurso> findByAutor(String autor);

    // Spring Data JPA crea la consulta: "SELECT * FROM Recurso WHERE tipo = ?"
    List<Recurso> findByTipo(String tipo);
}