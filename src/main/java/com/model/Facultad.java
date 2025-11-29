package com.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Modelo de Facultad para Study Buddy
 * Representa una facultad universitaria
 */
@Entity
@Table(name = "Facultad")
public class Facultad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facultad")
    private Long idFacultad;
    
    @Column(name = "nombre_facultad", nullable = false, unique = true, length = 100)
    private String nombre;
    
    @OneToMany(mappedBy = "facultad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Carrera> carreras;
    
    public Facultad() {}
    
    public Facultad(String nombre) {
        this.nombre = nombre;
    }
    
    // Getters y Setters
    public Long getIdFacultad() {
        return idFacultad;
    }
    
    public void setIdFacultad(Long idFacultad) {
        this.idFacultad = idFacultad;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public List<Carrera> getCarreras() {
        return carreras;
    }
    
    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }
    
    @Override
    public String toString() {
        return "Facultad{" +
                "idFacultad=" + idFacultad +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

