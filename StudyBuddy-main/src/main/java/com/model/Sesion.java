package com.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Sesion")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Integer id;

    @Column(name = "tema")
    private String tema;

    @Column(name = "fecha")
    private LocalDate fecha; // <-- CAMBIO: De String a LocalDate

    @Column(name = "hora")
    private LocalTime hora; // <-- CAMBIO: De String a LocalTime

    @Column(name = "descripcion")
    private String descripcion;

    // NOTA: Para mantenerlo simple y no romper tu formulario 'sesiones.html',
    // no he añadido la relación con GrupoEstudio, pero podrías añadirla
    // igual que en 'Recurso.java' si actualizas el formulario.

    public Sesion() {
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}