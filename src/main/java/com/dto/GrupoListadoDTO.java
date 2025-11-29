package com.dto;

/**
 * DTO para el listado de grupos con información relacionada
 * Incluye datos de GrupoEstudio, Materia y Usuario (creador)
 */
public class GrupoListadoDTO {
    private Long idGrupo;
    private String nombreGrupo;
    private String nombreMateria;
    private String horario;
    private String descripcion;
    private String nombreCreador;
    private Long cantidadMiembros;
    
    public GrupoListadoDTO() {}
    
    public GrupoListadoDTO(Long idGrupo, String nombreGrupo, String nombreMateria, 
                          String horario, String descripcion, String nombreCreador, 
                          Long cantidadMiembros) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.nombreMateria = nombreMateria;
        this.horario = horario;
        this.descripcion = descripcion;
        this.nombreCreador = nombreCreador;
        this.cantidadMiembros = cantidadMiembros;
    }
    
    // Getters y Setters
    public Long getIdGrupo() {
        return idGrupo;
    }
    
    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public String getNombreGrupo() {
        return nombreGrupo;
    }
    
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }
    
    public String getNombreMateria() {
        return nombreMateria;
    }
    
    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
    
    public String getHorario() {
        return horario;
    }
    
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getNombreCreador() {
        return nombreCreador;
    }
    
    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }
    
    public Long getCantidadMiembros() {
        return cantidadMiembros;
    }
    
    public void setCantidadMiembros(Long cantidadMiembros) {
        this.cantidadMiembros = cantidadMiembros;
    }
    
    // Método de compatibilidad para buscar.html
    public String getNombre() {
        return nombreGrupo;
    }
    
    public int getMiembros() {
        return cantidadMiembros != null ? cantidadMiembros.intValue() : 0;
    }
    
    // Método de compatibilidad para materia (sin "nombre")
    public String getMateria() {
        return nombreMateria;
    }
}

