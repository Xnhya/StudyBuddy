package com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de Usuario para Study Buddy.
 * Representa a un estudiante con sus datos personales, académicos y preferencias.
 */
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private List<String> materias = new ArrayList<>();
    private List<String> horarios = new ArrayList<>();
    private List<String> preferenciasEstudio = new ArrayList<>();

    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }

    /**
     * Constructor para crear un usuario con nombre, email y contraseña.
     *
     * @param nombre   Nombre del usuario.
     * @param email    Email del usuario (usado para login).
     * @param password Contraseña del usuario.
     */
    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getMaterias() {
        return materias;
    }

    public void setMaterias(List<String> materias) {
        this.materias = materias != null ? materias : new ArrayList<>();
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios != null ? horarios : new ArrayList<>();
    }

    public List<String> getPreferenciasEstudio() {
        return preferenciasEstudio;
    }

    public void setPreferenciasEstudio(List<String> preferenciasEstudio) {
        this.preferenciasEstudio = preferenciasEstudio != null ? preferenciasEstudio : new ArrayList<>();
    }

    // --- Métodos de utilidad para agregar elementos individuales ---

    public void agregarMateria(String materia) {
        if (materia != null && !materia.trim().isEmpty() && !this.materias.contains(materia)) {
            this.materias.add(materia);
        }
    }

    public void agregarHorario(String horario) {
        if (horario != null && !horario.trim().isEmpty() && !this.horarios.contains(horario)) {
            this.horarios.add(horario);
        }
    }

    public void agregarPreferencia(String preferencia) {
        if (preferencia != null && !preferencia.trim().isEmpty() && !this.preferenciasEstudio.contains(preferencia)) {
            this.preferenciasEstudio.add(preferencia);
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", materias=" + materias +
                ", horarios=" + horarios +
                ", preferenciasEstudio=" + preferenciasEstudio +
                '}';
    }
}