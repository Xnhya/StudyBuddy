package com.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Facultad")
public class Facultad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facultad")
    private Integer idFacultad;

    @Column(name = "nombre_facultad")
    private String nombreFacultad;

    // --- Constructores, Getters y Setters ---
    public Facultad() {}
    public Integer getIdFacultad() { return idFacultad; }
    public void setIdFacultad(Integer idFacultad) { this.idFacultad = idFacultad; }
    public String getNombreFacultad() { return nombreFacultad; }
    public void setNombreFacultad(String nombreFacultad) { this.nombreFacultad = nombreFacultad; }
}