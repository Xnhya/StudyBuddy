package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Recurso")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso")
    private Integer id; // <-- CAMBIO: De Long a Integer por consistencia

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo")
    private String tipo; // DOCUMENTO, ENLACE, VIDEO, IMAGEN, etc.

    @Column(name = "autor")
    private String autor;

    // --- CAMBIO CLAVE ---
    // Ya no es un 'Long grupoId', ahora es la relación real.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grupo")
    private GrupoEstudio grupo;

    public Recurso() {}

    // Getters y Setters actualizados
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public GrupoEstudio getGrupo() { return grupo; }
    public void setGrupo(GrupoEstudio grupo) { this.grupo = grupo; }

    // --- Métodos de utilidad (sin cambios) ---
    public String getTipoIcono() {
        switch (tipo != null ? tipo.toUpperCase() : "") {
            case "DOCUMENTO": return "📄";
            case "ENLACE": return "🔗";
            case "VIDEO": return "🎥";
            case "IMAGEN": return "🖼️";
            case "AUDIO": return "🎵";
            default: return "📁";
        }
    }

    public String getTipoColor() {
        switch (tipo != null ? tipo.toUpperCase() : "") {
            case "DOCUMENTO": return "primary";
            case "ENLACE": return "info";
            case "VIDEO": return "danger";
            case "IMAGEN": return "success";
            case "AUDIO": return "warning";
            default: return "secondary";
        }
    }
}