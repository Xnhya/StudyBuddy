package com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de GrupoEstudio para Study Buddy
 * Representa un grupo de estudio con materia, horario, descripción y lista de usuarios
 */
public class Grupo {
    private Long id;
    private String nombre;
    private String materia;
    private String horario;
    private String descripcion;
    private List<String> listaUsuarios = new ArrayList<>();

    public Grupo() {}

    public Grupo(String nombre, String materia, String horario) {
        this.nombre = nombre;
        this.materia = materia;
        this.horario = horario;
    }

    public Grupo(String nombre, String materia, String horario, String descripcion) {
        this(nombre, materia, horario);
        this.descripcion = descripcion;
    }

    public Grupo(Long id, String nombre, String materia, String horario, String descripcion) {
        this(nombre, materia, horario, descripcion);
        this.id = id;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<String> getListaUsuarios() { return listaUsuarios; }
    public void setListaUsuarios(List<String> listaUsuarios) { this.listaUsuarios = listaUsuarios; }

    // Métodos de compatibilidad con código existente
    public List<String> getListaMiembros() { return listaUsuarios; }
    public int getMiembros() { return listaUsuarios.size(); }

    // Métodos de gestión de usuarios
    public void addMiembro(String usuario) {
        if (usuario != null && !listaUsuarios.contains(usuario)) {
            listaUsuarios.add(usuario);
        }
    }

    public void removerMiembro(String usuario) {
        listaUsuarios.remove(usuario);
    }

    public boolean tieneMiembro(String usuario) {
        return listaUsuarios.contains(usuario);
    }

    public boolean estaLleno(int limiteMaximo) {
        return listaUsuarios.size() >= limiteMaximo;
    }

    // Métodos de utilidad
    public String getDescripcionCorta() {
        if (descripcion == null || descripcion.length() <= 100) {
            return descripcion;
        }
        return descripcion.substring(0, 100) + "...";
    }

    public String getEstado() {
        int miembros = listaUsuarios.size();
        if (miembros == 0) {
            return "Vacío";
        } else if (miembros < 3) {
            return "Disponible";
        } else if (miembros < 6) {
            return "Activo";
        } else {
            return "Lleno";
        }
    }

    public String getEstadoColor() {
        int miembros = listaUsuarios.size();
        if (miembros == 0) {
            return "secondary";
        } else if (miembros < 3) {
            return "success";
        } else if (miembros < 6) {
            return "warning";
        } else {
            return "danger";
        }
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", materia='" + materia + '\'' +
                ", horario='" + horario + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", listaUsuarios=" + listaUsuarios +
                '}';
    }
}
