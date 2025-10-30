package com.service;

import com.model.Usuario;
import com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
// --- ¡CAMBIOS! Importaciones de Spring Security ---
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList; // Necesario para los roles (aunque vacíos)
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de usuarios con MySQL
 * ¡CORREGIDO! Ahora implementa UserDetailsService para Spring Security.
 */
@Service
@Transactional(readOnly = true) 
public class UsuarioService implements UserDetailsService { // <-- ¡CAMBIO CRÍTICO!
    
    @Autowired
    private UsuarioRepository usuarioRepository;

   // Contenido esperado dentro de la clase UsuarioService

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca el usuario en tu base de datos
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró usuario con email: " + email));

        // --- ¡CAMBIOS AQUÍ PARA DEBUGGING! ---
        // Esto mostrará el hash que Hibernate recuperó de la BDD.
        System.out.println("DEBUG HASH EN BDD: " + usuario.getPassword()); 
        // --- FIN CAMBIOS ---
        
        // Retorna un objeto 'User' de Spring Security con el hash (la contraseña).
        return new User(usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
    }

    /**
     * Agregar un nuevo usuario (CREATE)
     */
    @Transactional
    public Usuario agregar(Usuario usuario) {
        if (usuario != null) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    /**
     * Listar todos los usuarios (READ)
     */
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    /**
     * Buscar usuario por ID (READ)
     */
    public Usuario buscarPorId(Integer id) {
        if (id == null) return null;
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    /**
     * Buscar usuario por email (READ)
     */
    public Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    /**
     * Buscar usuario por nombre
     */
    public Usuario buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return null;
        return usuarioRepository.findByNombre(nombre).orElse(null);
    }

    /**
     * Actualizar un usuario existente (UPDATE)
     */
    @Transactional
    public Usuario actualizar(Usuario usuario) {
        if (usuario != null && usuario.getId() != null) {
            if (usuarioRepository.existsById(usuario.getId())) {
                return usuarioRepository.save(usuario);
            }
        }
        return null;
    }

    /**
     * Eliminar usuario por ID (DELETE)
     */
    @Transactional
    public boolean eliminar(Integer id) {
        if (id == null) return false;
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- ¡MÉTODO 'autenticar' ELIMINADO! --- (Ya no es necesario y era inseguro).
}