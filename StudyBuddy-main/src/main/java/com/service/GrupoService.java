package com.service;

import com.model.GrupoEstudio;
import com.repository.GrupoEstudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.dto.GrupoListadoDTO;

/**
 * Servicio para Grupos de Estudio, conectado a MySQL.
 */
@Service
@Transactional(readOnly = true)
public class GrupoService {

    @Autowired
    private GrupoEstudioRepository grupoRepo;

    // --- MÉTODOS CRUD BÁSICOS ---

    @Transactional
    public GrupoEstudio agregar(GrupoEstudio grupo) {
        return grupoRepo.save(grupo);
    }

    public List<GrupoEstudio> listar() {
        return grupoRepo.findAll();
    }

    // --- CAMBIO AQUÍ: Long -> Integer ---
    public GrupoEstudio buscarPorId(Integer id) {
        if (id == null) return null;
        return grupoRepo.findById(id).orElse(null);
    }

    @Transactional
    public GrupoEstudio actualizar(GrupoEstudio grupo) {
        // grupo.getId() ahora devuelve Integer, coincide con el repo
        if (grupo != null && grupo.getId() != null && grupoRepo.existsById(grupo.getId())) {
            return grupoRepo.save(grupo);
        }
        return null;
    }

    // --- CAMBIO AQUÍ: Long -> Integer ---
    @Transactional
    public boolean eliminar(Integer id) {
        if (id != null && grupoRepo.existsById(id)) {
            grupoRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // --- LÓGICA DE NEGOCIO (DTOs) ---
    public List<GrupoListadoDTO> listarDTO() {
        return grupoRepo.findGruposConDetalles();
    }
}