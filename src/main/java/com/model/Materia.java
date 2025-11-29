package com.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Modelo de Materia para Study Buddy
 * Representa una materia de estudio
 */
@Entity
@Table(name = "Materia")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Long idMateria;
    
    @Column(name = "nombre_materia", nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera", nullable = true)
    private Carrera carrera;
    
    @ManyToMany(mappedBy = "materias", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
    
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GrupoEstudio> grupos;
    
    public Materia() {}
    
    public Materia(String nombre) {
        this.nombre = nombre;
    }
    
    // Getters y Setters
    public Long getIdMateria() {
        return idMateria;
    }
    
    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public List<GrupoEstudio> getGrupos() {
        return grupos;
    }
    
    public void setGrupos(List<GrupoEstudio> grupos) {
        this.grupos = grupos;
    }
    
    @Override
    public String toString() {
        return "Materia{" +
                "idMateria=" + idMateria +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

