package com.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para listado de sesiones con datos relacionados de grupo y usuario
 */
public class SesionListadoDTO {
    private Long idSesion;
    private String tema;
    private LocalDate fecha;
    private LocalTime hora;
    private String grupoNombre;
    private String usuarioNombre;

    public SesionListadoDTO(Long idSesion, String tema, LocalDate fecha, LocalTime hora,
                            String grupoNombre, String usuarioNombre) {
        this.idSesion = idSesion;
        this.tema = tema;
        this.fecha = fecha;
        this.hora = hora;
        this.grupoNombre = grupoNombre;
        this.usuarioNombre = usuarioNombre;
    }

    public Long getIdSesion() { return idSesion; }
    public String getTema() { return tema; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public String getGrupoNombre() { return grupoNombre; }
    public String getUsuarioNombre() { return usuarioNombre; }
}


