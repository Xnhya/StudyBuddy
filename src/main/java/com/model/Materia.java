package com.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Modelo de Materia para Study Buddy
 * Representa una materia de estudio
 */
@Entity
@Table(name = "materias")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMateria;
    
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;
    
    @ManyToMany(mappedBy = "materias", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
    
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Grupo> grupos;
    
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
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public List<Grupo> getGrupos() {
        return grupos;
    }
    
    public void setGrupos(List<Grupo> grupos) {
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

