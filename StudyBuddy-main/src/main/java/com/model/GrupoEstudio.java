package com.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "grupoestudio") // Name matches the lowercase table name found in MySQL
public class GrupoEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Integer id; // <-- CAMBIO AQUÍ: Long -> Integer

    @Column(name = "nombre_grupo")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creador")
    private Usuario creador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia")
    private Materia materia;

    @ManyToMany
    @JoinTable(
        name = "Usuario_Grupo",
        joinColumns = @JoinColumn(name = "id_grupo"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<Usuario> miembros;

    // --- Constructores ---
    public GrupoEstudio() {}

    // --- Getters y Setters ---
    public Integer getId() { // <-- CAMBIO AQUÍ: Long -> Integer
        return id;
    }

    public void setId(Integer id) { // <-- CAMBIO AQUÍ: Long -> Integer
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Set<Usuario> getMiembros() {
        return miembros;
    }

    public void setMiembros(Set<Usuario> miembros) {
        this.miembros = miembros;
    }
}