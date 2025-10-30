package com.repository;

import com.model.Carrera; // Apunta a tu modelo Carrera.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
// Le decimos a Spring que maneje la entidad 'Carrera', cuyo ID es 'Integer'
public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    /**
     * ¡Este es el método "mágico" para la consulta en cascada!
     * * Spring Data JPA creará automáticamente la consulta SQL:
     * "SELECT * FROM Carrera WHERE id_facultad = ?"
     * * Lo usamos en 'ApiDatosController' (que crearemos después)
     * y en 'PaginasController' para buscar la carrera por su ID.
     */
    List<Carrera> findByFacultadIdFacultad(Integer idFacultad);
}