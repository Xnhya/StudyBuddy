package com.service;

import com.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Servicio para gestión de usuarios en memoria
 * Implementa operaciones CRUD para usuarios del sistema
 */
@Service
public class UsuarioService {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private Usuario usuarioActual; // Usuario logueado actualmente

    /**
     * Inicializar usuarios de prueba
     */
    public UsuarioService() {
        inicializarUsuariosPrueba();
    }

    /**
     * Agregar un nuevo usuario
     */
    public synchronized Usuario agregar(Usuario usuario) {
        if (usuario != null) {
            usuario.setId(idGenerator.getAndIncrement());
            usuarios.add(usuario);
            return usuario;
        }
        return null;
    }

    /**
     * Listar todos los usuarios
     */
    public synchronized List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Buscar usuario por ID
     */
    public synchronized Usuario buscarPorId(Long id) {
        if (id == null) return null;
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Buscar usuario por email
     */
    public synchronized Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;
        return usuarios.stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Buscar usuario por nombre
     */
    public synchronized Usuario buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return null;
        return usuarios.stream()
                .filter(u -> nombre.equalsIgnoreCase(u.getNombre()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Actualizar un usuario existente
     */
    public synchronized Usuario actualizar(Usuario usuario) {
        if (usuario != null && usuario.getId() != null) {
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getId().equals(usuario.getId())) {
                    usuarios.set(i, usuario);
                    return usuario;
                }
            }
        }
        return null;
    }

    /**
     * Eliminar usuario por ID
     */
    public synchronized boolean eliminar(Long id) {
        if (id == null) return false;
        return usuarios.removeIf(u -> u.getId().equals(id));
    }

    /**
     * Autenticar usuario (login)
     */
    public synchronized Usuario autenticar(String email, String password) {
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
     * Cerrar sesión (logout)
     */
    public void borrar() {
        this.usuarioActual = null;
    }

    /**
     * Buscar usuarios por materia
     */
    public synchronized List<Usuario> buscarPorMateria(String materia) {
        if (materia == null || materia.trim().isEmpty()) return new ArrayList<>();
        return usuarios.stream()
                .filter(u -> u.getMaterias() != null && u.getMaterias().contains(materia))
                .toList();
    }

    /**
     * Buscar usuarios por horario
     */
    public synchronized List<Usuario> buscarPorHorario(String horario) {
        if (horario == null || horario.trim().isEmpty()) return new ArrayList<>();
        return usuarios.stream()
                .filter(u -> u.getHorarios() != null && u.getHorarios().contains(horario))
                .toList();
    }

    /**
     * Buscar usuarios por preferencia de estudio
     */
    public synchronized List<Usuario> buscarPorPreferencia(String preferencia) {
        if (preferencia == null || preferencia.trim().isEmpty()) return new ArrayList<>();
        return usuarios.stream()
                .filter(u -> u.getPreferenciasEstudio() != null && u.getPreferenciasEstudio().contains(preferencia))
                .toList();
    }

    /**
     * Inicializar usuarios de prueba para la demo
     */
    private void inicializarUsuariosPrueba() {
        Usuario admin = new Usuario("Admin", "admin@studybuddy.com", "123456");
        admin.agregarMateria("Matemáticas");
        admin.agregarMateria("Programación");
        admin.agregarHorario("Mañana");
        admin.agregarPreferencia("Silencio");
        agregar(admin);

        Usuario estudiante1 = new Usuario("María García", "maria@estudiante.com", "123456");
        estudiante1.agregarMateria("Cálculo");
        estudiante1.agregarMateria("Física");
        estudiante1.agregarHorario("Tarde");
        estudiante1.agregarPreferencia("Música");
        agregar(estudiante1);

        Usuario estudiante2 = new Usuario("Carlos López", "carlos@estudiante.com", "123456");
        estudiante2.agregarMateria("Programación");
        estudiante2.agregarMateria("Base de Datos");
        estudiante2.agregarHorario("Noche");
        estudiante2.agregarPreferencia("Gamificación");
        agregar(estudiante2);
    }
}
