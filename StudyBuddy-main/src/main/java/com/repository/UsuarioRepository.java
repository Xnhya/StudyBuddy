package com.repository;

import com.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 * Contiene todos los métodos CRUD básicos + métodos de búsqueda personalizados.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> { // <-- CAMBIO AQUÍ

    // JpaRepository<Usuario, Integer> significa:
    // "Quiero todos los métodos CRUD (.save, .findAll, .deleteById, etc.)
    // para mi entidad 'Usuario', y su llave primaria (ID) es de tipo 'Integer'".
    


    /**
     * Método de búsqueda "mágico" (Query Method).
     * Spring Data JPA automáticamente crea la consulta SQL:
     * "SELECT * FROM Usuario WHERE email = ?"
     * Lo usamos en el Service para el método 'autenticar'.
     * Usamos Optional<> porque puede que no encuentre al usuario.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Otro método mágico para el 'buscarPorNombre' del servicio.
     * Spring crea el SQL: "SELECT * FROM Usuario WHERE nombre = ?"
     */
    Optional<Usuario> findByNombre(String nombre);
}