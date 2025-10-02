package com.model;

public class Sesion {
    private String tema;
    private String fecha;
    private String hora;
    private String descripcion;

    public Sesion() {}

    public Sesion(String tema, String fecha, String hora, String descripcion) {
        this.tema = tema;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
