package com.repository;

import com.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    Usuario findByNombre(String nombre);
}
