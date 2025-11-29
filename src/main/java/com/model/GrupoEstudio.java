package com.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GrupoEstudio")
public class GrupoEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")  // ✅ así se llama el PK en tu tabla SQL
    private Long id;

    @Column(name = "nombre_grupo", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "horario", nullable = true, length = 50)
    private String horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creador", referencedColumnName = "id_usuario")
    private Usuario creador;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "Usuario_Grupo",
        joinColumns = @JoinColumn(name = "id_grupo"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> miembros = new ArrayList<>();

    public GrupoEstudio() {}

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public Materia getMateria() { return materia; }
    public void setMateria(Materia materia) { this.materia = materia; }

    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = creador; }

    public List<Usuario> getMiembros() { return miembros; }
    public void setMiembros(List<Usuario> miembros) { this.miembros = miembros; }

    public void addMiembro(Usuario u) {
        if (u != null && !miembros.contains(u)) miembros.add(u);
    }
}
