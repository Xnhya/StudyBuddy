package com.repository;

import com.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Usuario findByNombre(String nombre);

    
    // --- AGREGAR ESTE MÉTODO ---
    // (Adaptado de UsuarioRepository.java de tu demo)
    Usuario findByTokenRecuperacion(String token);
}



