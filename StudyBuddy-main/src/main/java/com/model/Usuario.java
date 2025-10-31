package com.model;

import jakarta.persistence.*;
import java.util.Set; // ¡Importante!

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id; // <-- CAMBIO AQUÍ

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "preferencias")
    private String preferencias;

    // Relación: Un Usuario pertenece a UNA Carrera
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    // Relación: Un Usuario puede estar en MUCHOS Grupos
    @ManyToMany(mappedBy = "miembros") // "mappedBy" apunta a la variable en 'GrupoEstudio'
    private Set<GrupoEstudio> grupos;

    // --- Constructores, Getters y Setters ---
    public Usuario() {
    }

    // Getters y Setters para todos los campos (id, nombre, apellido, email,
    // password, carrera, grupos)...

    public Integer getId() {
        return id;
    } // <-- CAMBIO AQUÍ

    public void setId(Integer id) {
        this.id = id;
    } // <-- CAMBIO AQUÍ

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Set<GrupoEstudio> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<GrupoEstudio> grupos) {
        this.grupos = grupos;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }
}