package com.dto;

public class GrupoListadoDTO {

    private Integer idGrupo;
    private String nombreGrupo;
    private String nombreMateria;
    private String nombreCreador;
    private Integer idCreador;

    public GrupoListadoDTO(Integer idGrupo, String nombreGrupo, String nombreMateria, String nombreCreador, Integer idCreador) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.nombreMateria = nombreMateria;
        this.nombreCreador = nombreCreador;
        this.idCreador = idCreador;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public Integer getIdCreador() {
        return idCreador;
    }
}

