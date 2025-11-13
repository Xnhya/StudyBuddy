package com.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de GrupoEstudio para Study Buddy
 * Representa un grupo de estudio con materia, horario, descripción y lista de usuarios
 */
@Entity
@Table(name = "grupos")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;
    
    @Column(length = 50)
    private String horario;
    
    @Column(length = 500)
    private String descripcion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creador", nullable = false)
    private Usuario creador;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "grupo_miembros",
        joinColumns = @JoinColumn(name = "id_grupo"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> miembros = new ArrayList<>();
    
    public Grupo() {
        this.miembros = new ArrayList<>();
    }
    
    public Grupo(String nombre, Materia materia, String horario) {
        this();
        this.nombre = nombre;
        this.materia = materia;
        this.horario = horario;
    }
    
    public Grupo(String nombre, Materia materia, String horario, String descripcion) {
        this(nombre, materia, horario);
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
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
    
    public Materia getMateria() { 
        return materia; 
    }
    
    public void setMateria(Materia materia) { 
        this.materia = materia; 
    }
    
    public String getHorario() { 
        return horario; 
    }
    
    public void setHorario(String horario) { 
        this.horario = horario; 
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
    
    public List<Usuario> getMiembros() { 
        return miembros; 
    }
    
    public void setMiembros(List<Usuario> miembros) { 
        this.miembros = miembros != null ? miembros : new ArrayList<>(); 
    }
    
    // Métodos de compatibilidad con código existente
    public List<String> getListaUsuarios() {
        List<String> nombres = new ArrayList<>();
        for (Usuario u : miembros) {
            nombres.add(u.getNombre());
        }
        return nombres;
    }
    
    public void setListaUsuarios(List<String> listaUsuarios) {
        // Este método se mantiene para compatibilidad pero no hace nada real
        // Los miembros se gestionan a través de la relación JPA
    }
    
    public List<String> getListaMiembros() { 
        return getListaUsuarios(); 
    }
    
    // Método de compatibilidad que retorna la cantidad de miembros como int
    public int getCantidadMiembros() { 
        return miembros != null ? miembros.size() : 0; 
    }
    
    // Métodos de gestión de usuarios
    public void addMiembro(Usuario usuario) {
        if (usuario != null && !miembros.contains(usuario)) {
            miembros.add(usuario);
        }
    }
    
    public void addMiembro(String nombreUsuario) {
        // Método de compatibilidad - requiere buscar el usuario por nombre
        // Mejor usar addMiembro(Usuario usuario) directamente
    }
    
    public void removerMiembro(Usuario usuario) {
        miembros.remove(usuario);
    }
    
    public boolean tieneMiembro(Usuario usuario) {
        return miembros.contains(usuario);
    }
    
    public boolean estaLleno(int limiteMaximo) {
        return miembros.size() >= limiteMaximo;
    }
    
    // Métodos de utilidad
    public String getDescripcionCorta() {
        if (descripcion == null || descripcion.length() <= 100) {
            return descripcion;
        }
        return descripcion.substring(0, 100) + "...";
    }
    
    public String getEstado() {
        int numMiembros = miembros.size();
        if (numMiembros == 0) {
            return "Vacío";
        } else if (numMiembros < 3) {
            return "Disponible";
        } else if (numMiembros < 6) {
            return "Activo";
        } else {
            return "Lleno";
        }
    }
    
    public String getEstadoColor() {
        int numMiembros = miembros.size();
        if (numMiembros == 0) {
            return "secondary";
        } else if (numMiembros < 3) {
            return "success";
        } else if (numMiembros < 6) {
            return "warning";
        } else {
            return "danger";
        }
    }
    
    @Override
    public String toString() {
        return "Grupo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", materia=" + (materia != null ? materia.getNombre() : null) +
                ", horario='" + horario + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", miembros=" + miembros.size() +
                '}';
    }
}

