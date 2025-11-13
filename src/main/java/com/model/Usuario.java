package com.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de Usuario para Study Buddy
 * Representa un estudiante con sus datos personales, materias, horarios y preferencias de estudio
 */
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String password;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "usuario_materias",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_materia")
    )
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
    private List<Grupo> grupos = new ArrayList<>();
    
    public Usuario() {
        this.materias = new ArrayList<>();
        this.horarios = new ArrayList<>();
        this.preferenciasEstudio = new ArrayList<>();
    }
    
    public Usuario(String nombre, String email, String password) {
        this();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }
    
    public Usuario(Long id, String nombre, String email, String password) {
        this(nombre, email, password);
        this.id = id;
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
    
    public List<Grupo> getGrupos() {
        return grupos;
    }
    
    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
    
    // Métodos de utilidad
    public void agregarMateria(Materia materia) {
        if (materia != null && !materias.contains(materia)) {
            materias.add(materia);
        }
    }
    
    public void agregarHorario(String horario) {
        if (horario != null && !horarios.contains(horario)) {
            horarios.add(horario);
        }
    }
    
    public void agregarPreferencia(String preferencia) {
        if (preferencia != null && !preferenciasEstudio.contains(preferencia)) {
            preferenciasEstudio.add(preferencia);
        }
    }
    
    public void removerMateria(Materia materia) {
        materias.remove(materia);
    }
    
    public void removerHorario(String horario) {
        horarios.remove(horario);
    }
    
    public void removerPreferencia(String preferencia) {
        preferenciasEstudio.remove(preferencia);
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", materias=" + materias.size() +
                ", horarios=" + horarios +
                ", preferenciasEstudio=" + preferenciasEstudio +
                '}';
    }
}
