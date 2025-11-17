package com.model;

import jakarta.persistence.*;
import java.time.LocalDateTime; // Para la fecha de expiración
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Usuario") // Debe coincidir con la tabla en MySQL
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // Mapeamos a la nueva PK
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido; // ¡Nuevo campo!

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "contraseña", nullable = false, length = 255) // Mapeamos a la columna 'contraseña'
    private String password;

    @Column(length = 20)
    private String rol; // 'ADMIN' o 'USER'

    // Campos para recuperación de contraseña
    @Column(name = "token_recuperacion")
    private String tokenRecuperacion;

    @Column(name = "token_expiracion")
    private LocalDateTime tokenExpiracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @Column(name = "preferencias")
    private String preferenciasEstudio; // Ahora es un String simple

    @ManyToMany(mappedBy = "miembros", fetch = FetchType.LAZY)
    private List<Grupo> grupos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_materia", // Nombre de la tabla intermedia en la BD
            joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_materia"))
    private List<Materia> materias = new ArrayList<>();

    public Usuario() {
    }

    // Constructor auxiliar para pruebas rápidas
    public Usuario(String nombre, String apellido, String email, String password, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // --- Getters y Setters ---

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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public LocalDateTime getTokenExpiracion() {
        return tokenExpiracion;
    }

    public void setTokenExpiracion(LocalDateTime tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getPreferenciasEstudio() {
        return preferenciasEstudio;
    }

    public void setPreferenciasEstudio(String preferenciasEstudio) {
        this.preferenciasEstudio = preferenciasEstudio;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}