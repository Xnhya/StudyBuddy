package com.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // Persistimos el hash aquí
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    // Campo temporal para recibir la contraseña desde JSON al registrar/login (NO
    // persistido)
    @Transient
    private String password;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_materias", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_materia"))
    private List<Materia> materias = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "usuario_horarios", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column(name = "horario")
    private List<String> horarios = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "usuario_preferencias", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column(name = "preferencia")
    private List<String> preferenciasEstudio = new ArrayList<>();

    @ManyToMany(mappedBy = "miembros", fetch = FetchType.LAZY)
    private List<GrupoEstudio> grupos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // --- Getters / Setters ---
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter/Setter para el hash persistido
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Getter/Setter para la contraseña en bruto (transient -> JSON)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido != null ? apellido : "";
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias != null ? materias : new ArrayList<>();
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios != null ? horarios : new ArrayList<>();
    }

    public List<String> getPreferenciasEstudio() {
        return preferenciasEstudio;
    }

    public void setPreferenciasEstudio(List<String> preferenciasEstudio) {
        this.preferenciasEstudio = preferenciasEstudio != null ? preferenciasEstudio : new ArrayList<>();
    }

    public List<GrupoEstudio> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoEstudio> grupos) {
        this.grupos = grupos;
    }

    // utilidades
    public void agregarMateria(Materia materia) {
        if (materia != null && !materias.contains(materia))
            materias.add(materia);
    }

    public void agregarHorario(String horario) {
        if (horario != null && !horarios.contains(horario))
            horarios.add(horario);
    }

    public void agregarPreferencia(String preferencia) {
        if (preferencia != null && !preferenciasEstudio.contains(preferencia))
            preferenciasEstudio.add(preferencia);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", materias=" + materias.size() +
                '}';
    }
}
