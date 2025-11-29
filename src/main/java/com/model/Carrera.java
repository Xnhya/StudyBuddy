package com.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Modelo de Carrera para Study Buddy
 * Representa una carrera universitaria perteneciente a una facultad
 */
@Entity
@Table(name = "Carrera")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrera")
    private Long idCarrera;
    
    @Column(name = "nombre_carrera", nullable = false, length = 100)
    private String nombre;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_facultad", nullable = false)
    private Facultad facultad;
    
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
    
    public Carrera() {}
    
    public Carrera(String nombre, Facultad facultad) {
        this.nombre = nombre;
        this.facultad = facultad;
    }
    
    // Getters y Setters
    public Long getIdCarrera() {
        return idCarrera;
    }
    
    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Facultad getFacultad() {
        return facultad;
    }
    
    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    @Override
    public String toString() {
        return "Carrera{" +
                "idCarrera=" + idCarrera +
                ", nombre='" + nombre + '\'' +
                ", facultad=" + (facultad != null ? facultad.getNombre() : null) +
                '}';
    }
}

