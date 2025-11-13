package com.model;

import jakarta.persistence.*;

/**
 * Modelo de Recurso para Study Buddy
 * Representa un recurso de estudio (documento, enlace, video, etc.) asociado a un grupo
 */
@Entity
@Table(name = "recursos")
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(length = 1000)
    private String descripcion;
    
    @Column(nullable = false, length = 50)
    private String tipo; // DOCUMENTO, ENLACE, VIDEO, IMAGEN, AUDIO
    
    @Column(length = 100)
    private String autor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;
    
    public Recurso() {}
    
    public Recurso(String titulo, String descripcion, String tipo, String autor, Grupo grupo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.autor = autor;
        this.grupo = grupo;
    }
    
    // Getters y Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getTitulo() { 
        return titulo; 
    }
    
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
    }
    
    public String getDescripcion() { 
        return descripcion; 
    }
    
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }
    
    public String getTipo() { 
        return tipo; 
    }
    
    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }
    
    public String getAutor() { 
        return autor; 
    }
    
    public void setAutor(String autor) { 
        this.autor = autor; 
    }
    
    public Grupo getGrupo() {
        return grupo;
    }
    
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    
    // Método de compatibilidad
    public Long getGrupoId() {
        return grupo != null ? grupo.getId() : null;
    }
    
    public void setGrupoId(Long grupoId) {
        // Este método se mantiene para compatibilidad pero no hace nada real
        // El grupo se gestiona a través de la relación JPA
    }
    
    // Métodos de utilidad
    public String getTipoIcono() {
        switch (tipo != null ? tipo.toUpperCase() : "") {
            case "DOCUMENTO":
                return "📄";
            case "ENLACE":
                return "🔗";
            case "VIDEO":
                return "🎥";
            case "IMAGEN":
                return "🖼️";
            case "AUDIO":
                return "🎵";
            default:
                return "📁";
        }
    }
    
    public String getTipoColor() {
        switch (tipo != null ? tipo.toUpperCase() : "") {
            case "DOCUMENTO":
                return "primary";
            case "ENLACE":
                return "info";
            case "VIDEO":
                return "danger";
            case "IMAGEN":
                return "success";
            case "AUDIO":
                return "warning";
            default:
                return "secondary";
        }
    }
    
    @Override
    public String toString() {
        return "Recurso{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", autor='" + autor + '\'' +
                ", grupoId=" + getGrupoId() +
                '}';
    }
}
