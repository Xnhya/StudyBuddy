package com.service;

import com.model.Usuario;
import com.model.Materia;
import com.repository.UsuarioRepository;
import com.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de usuarios con persistencia en base de datos
 * Implementa operaciones CRUD para usuarios del sistema
 */
@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private MateriaRepository materiaRepository;
    
    private Usuario usuarioActual; // Usuario logueado actualmente (sesión)
    
    /**
     * Agregar un nuevo usuario
     */
    public Usuario agregar(Usuario usuario) {
        if (usuario != null) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }
    
    /**
     * Listar todos los usuarios
     */
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
    
    /**
     * Buscar usuario por ID
     */
    public Usuario buscarPorId(Long id) {
        if (id == null) return null;
        return usuarioRepository.findById(id).orElse(null);
    }
    
    /**
     * Buscar usuario por email
     */
    public Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;
        return usuarioRepository.findByEmail(email);
    }
    
    /**
     * Buscar usuario por nombre
     */
    public Usuario buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return null;
        return usuarioRepository.findByNombre(nombre);
    }
    
    /**
     * Actualizar un usuario existente
     */
    public Usuario actualizar(Usuario usuario) {
        if (usuario != null && usuario.getId() != null) {
            if (usuarioRepository.existsById(usuario.getId())) {
                return usuarioRepository.save(usuario);
            }
        }
        return null;
    }
    
    /**
     * Eliminar usuario por ID
     */
    public boolean eliminar(Long id) {
        if (id == null) return false;
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Autenticar usuario (login)
     */
    public Usuario autenticar(String email, String password) {
        Usuario usuario = buscarPorEmail(email);
        if (usuario != null && password != null && password.equals(usuario.getPassword())) {
            this.usuarioActual = usuario;
            return usuario;
        }
        return null;
    }
    
    /**
     * Obtener usuario actual (logueado)
     */
    public Usuario obtener() {
        return this.usuarioActual;
    }
    
    /**
     * Guardar usuario actual (para compatibilidad con código existente)
     */
    public void guardar(Usuario usuario) {
        if (usuario != null) {
            if (usuario.getId() == null) {
                agregar(usuario);
            } else {
                actualizar(usuario);
            }
            this.usuarioActual = usuario;
        }
    }
    
    /**
     * Convertir nombres de materias (List<String>) a objetos Materia
     */
    public void asignarMateriasPorNombres(Usuario usuario, List<String> nombresMaterias) {
        if (usuario != null && nombresMaterias != null) {
            List<Materia> materias = new ArrayList<>();
            for (String nombre : nombresMaterias) {
                Materia materia = materiaRepository.findByNombre(nombre);
                if (materia == null) {
                    // Crear nueva materia si no existe
                    materia = new Materia(nombre);
                    materia = materiaRepository.save(materia);
                }
                materias.add(materia);
            }
            usuario.setMaterias(materias);
        }
    }
    
    /**
     * Cerrar sesión (logout)
     */
    public void borrar() {
        this.usuarioActual = null;
    }
    
    /**
     * Buscar usuarios por materia
     */
    public List<Usuario> buscarPorMateria(String nombreMateria) {
        if (nombreMateria == null || nombreMateria.trim().isEmpty()) return new ArrayList<>();
        Materia materia = materiaRepository.findByNombre(nombreMateria);
        if (materia != null) {
            return materia.getUsuarios();
        }
        return new ArrayList<>();
    }
    
    /**
     * Buscar usuarios por horario
     */
    public List<Usuario> buscarPorHorario(String horario) {
        if (horario == null || horario.trim().isEmpty()) return new ArrayList<>();
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getHorarios() != null && u.getHorarios().contains(horario))
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar usuarios por preferencia de estudio
     */
    public List<Usuario> buscarPorPreferencia(String preferencia) {
        if (preferencia == null || preferencia.trim().isEmpty()) return new ArrayList<>();
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getPreferenciasEstudio() != null && u.getPreferenciasEstudio().contains(preferencia))
                .collect(Collectors.toList());
    }
}
