package com.model;

/**
 * Modelo de Recurso para Study Buddy
 * Representa un recurso de estudio (documento, enlace, video, etc.) asociado a un grupo
 */
public class Recurso {
    private Long id;
    private String titulo;
    private String descripcion;
    private String tipo; // DOCUMENTO, ENLACE, VIDEO, IMAGEN, etc.
    private String autor;
    private Long grupoId;

    public Recurso() {}

    public Recurso(String titulo, String descripcion, String tipo, String autor, Long grupoId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.autor = autor;
        this.grupoId = grupoId;
    }

    public Recurso(Long id, String titulo, String descripcion, String tipo, String autor, Long grupoId) {
        this(titulo, descripcion, tipo, autor, grupoId);
        this.id = id;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Long getGrupoId() { return grupoId; }
    public void setGrupoId(Long grupoId) { this.grupoId = grupoId; }

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
                ", grupoId=" + grupoId +
                '}';
    }
}
