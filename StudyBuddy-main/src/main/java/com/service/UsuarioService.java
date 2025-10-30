package com.service;

import com.model.Usuario;
import com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de usuarios con MySQL
 * Implementa operaciones CRUD para usuarios del sistema
 * Este servicio es STATELESS (no guarda estado de sesión).
 */
@Service
@Transactional(readOnly = true) // Por defecto, todas las búsquedas son de solo lectura
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- ¡ERROR CORREGIDO! ---
    // Se elimina 'private Usuario usuarioActual;'
    // Los servicios no deben guardar estado de sesión.

    /**
     * Agregar un nuevo usuario (CREATE)
     */
    @Transactional // (Solo este método necesita escribir en la BDD)
    public Usuario agregar(Usuario usuario) {
        if (usuario != null) {
            // .save() hace un INSERT
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
    public Usuario buscarPorId(Integer id) { // <-- CAMBIO AQUÍ
        if (id == null) return null;
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    /**
     * Buscar usuario por email (READ)
     * (Asumiendo que findByEmail en el repo devuelve Optional<Usuario>)
     */
    public Usuario buscarPorEmail(String email) {
    if (email == null || email.trim().isEmpty()) return null;
    return usuarioRepository.findByEmail(email).orElse(null); // <--- AÑADE ESTO
}

    /**
     * Buscar usuario por nombre
     * (¡CUIDADO! Esto debe estar en tu UsuarioRepository)
     */
    public Usuario buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return null;
        // Esto asumirá que tienes 'Optional<Usuario> findByNombre(String nombre);' 
        // en tu interfazo UsuarioRepository. Si no, debes añadirlo.
        return usuarioRepository.findByNombre(nombre).orElse(null);
    }

    /**
     * Actualizar un usuario existente (UPDATE)
     */
    @Transactional // (Este método también escribe en la BDD)
    public Usuario actualizar(Usuario usuario) {
        if (usuario != null && usuario.getId() != null) {
            if (usuarioRepository.existsById(usuario.getId())) {
                // .save() hace un UPDATE si el ID ya existe
                return usuarioRepository.save(usuario);
            }
        }
        return null;
    }

    /**
     * Eliminar usuario por ID (DELETE)
     */
    @Transactional // (Este método también escribe en la BDD)
    public boolean eliminar(Integer id) { // <-- CAMBIO AQUÍ
        if (id == null) return false;
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Autenticar usuario (login)
     * ¡CORREGIDO! Ya no guarda 'usuarioActual'.
     */
    public Usuario autenticar(String email, String password) {
        Usuario usuario = this.buscarPorEmail(email);
        
        // ADVERTENCIA DE SEGURIDAD:
        // NUNCA compares contraseñas en texto plano.
        // Debes usar Spring Security con un PasswordEncoder (BCrypt).
        if (usuario != null && password != null && password.equals(usuario.getPassword())) {
            // ¡Login exitoso! Simplemente devuelve el usuario.
            return usuario;
        }
        // Falló el login
        return null;
    }

    // --- ¡MÉTODOS OBSOLETOS ELIMINADOS! ---
    //
    // Se eliminan 'obtener()', 'guardar()' y 'borrar()' porque
    // el manejo de la sesión (usuario logueado) se hace
    // en la capa del Controlador (Controller) con @SessionAttributes
    // o (mejor aún) con Spring Security.
    //
    // Se elimina 'inicializarUsuariosPrueba()'. Los datos de prueba
    // deben estar en tu script 'studyBuddy.sql' o en un archivo 'data.sql'
    // en la carpeta 'resources'.
    //
    // Se eliminan 'buscarPorMateria()', 'buscarPorHorario()' y 'buscarPorPreferencia()'.
    // Esta lógica ya no es válida.
    // La reemplazaremos con DTOs y JOINS, como pide tu rúbrica.
}