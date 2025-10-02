package com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de Usuario para Study Buddy
 * Representa un estudiante con sus datos personales, materias, horarios y preferencias de estudio
 */
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private List<String> materias;
    private List<String> horarios;
    private List<String> preferenciasEstudio;

    public Usuario() {
        this.materias = new ArrayList<>();
        this.horarios = new ArrayList<>();
        this.preferenciasEstudio = new ArrayList<>();
    }

    public Usuario(String nombre, String email, String password) {
        this();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Usuario(Long id, String nombre, String email, String password) {
        this(nombre, email, password);
        this.id = id;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getMaterias() { return materias; }
    public void setMaterias(List<String> materias) { this.materias = materias; }

    public List<String> getHorarios() { return horarios; }
    public void setHorarios(List<String> horarios) { this.horarios = horarios; }

    public List<String> getPreferenciasEstudio() { return preferenciasEstudio; }
    public void setPreferenciasEstudio(List<String> preferenciasEstudio) { 
        this.preferenciasEstudio = preferenciasEstudio; 
    }

    // Métodos de utilidad
    public void agregarMateria(String materia) {
        if (materia != null && !materias.contains(materia)) {
            materias.add(materia);
        }
    }

    public void agregarHorario(String horario) {
        if (horario != null && !horarios.contains(horario)) {
            horarios.add(horario);
        }
    }

    public void agregarPreferencia(String preferencia) {
        if (preferencia != null && !preferenciasEstudio.contains(preferencia)) {
            preferenciasEstudio.add(preferencia);
        }
    }

    public void removerMateria(String materia) {
        materias.remove(materia);
    }

    public void removerHorario(String horario) {
        horarios.remove(horario);
    }

    public void removerPreferencia(String preferencia) {
        preferenciasEstudio.remove(preferencia);
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
