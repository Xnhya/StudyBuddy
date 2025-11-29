package com.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Modelo de Sesion para Study Buddy
 * Representa una sesión de estudio programada
 */
@Entity
@Table(name = "Sesion")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String tema;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private LocalTime hora;
    
    @Column(length = 500)
    private String descripcion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grupo")
    private GrupoEstudio grupo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    public Sesion() {}
    
    public Sesion(String tema, LocalDate fecha, LocalTime hora, String descripcion) {
        this.tema = tema;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTema() { 
        return tema; 
    }
    
    public void setTema(String tema) { 
        this.tema = tema; 
    }
    
    public LocalDate getFecha() { 
        return fecha; 
    }
    
    public void setFecha(LocalDate fecha) { 
        this.fecha = fecha; 
    }
    
    public LocalTime getHora() { 
        return hora; 
    }
    
    public void setHora(LocalTime hora) { 
        this.hora = hora; 
    }
    
    public String getDescripcion() { 
        return descripcion; 
    }
    
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }
    
    public GrupoEstudio getGrupo() {
        return grupo;
    }
    
    public void setGrupo(GrupoEstudio grupo) {
        this.grupo = grupo;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    // Métodos de compatibilidad para String (fecha y hora)
    public String getFechaString() {
        return fecha != null ? fecha.toString() : null;
    }
    
    public void setFechaString(String fechaString) {
        if (fechaString != null && !fechaString.isEmpty()) {
            this.fecha = LocalDate.parse(fechaString);
        }
    }
    
    public String getHoraString() {
        return hora != null ? hora.toString() : null;
    }
    
    public void setHoraString(String horaString) {
        if (horaString != null && !horaString.isEmpty()) {
            this.hora = LocalTime.parse(horaString);
        }
    }
}
