package com.service;

import com.model.Recurso;
import com.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestión de recursos con persistencia en base de datos
 * Actualizado para coincidir con RecursoController
 */
@Service
@Transactional
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    /**
     * Guardar un recurso (sirve para CREAR y ACTUALIZAR)
     * Renombrado de 'agregar' a 'guardar' para coincidir con el controlador
     */
    public Recurso guardar(Recurso recurso) {
        if (recurso != null) {
            return recursoRepository.save(recurso);
        }
        return null;
    }

    /**
     * Listar todos los recursos
     * Renombrado de 'listar' a 'listarTodos' para coincidir con el controlador
     */
    public List<Recurso> listarTodos() {
        return recursoRepository.findAll();
    }

    /**
     * Buscar recurso por ID
     */
    public Recurso buscarPorId(Long id) {
        if (id == null) return null;
        return recursoRepository.findById(id).orElse(null);
    }

    /**
     * Eliminar recurso por ID
     */
    public boolean eliminar(Long id) {
        if (id == null) return false;
        if (recursoRepository.existsById(id)) {
            recursoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- MÉTODOS DE BÚSQUEDA ADICIONALES (Útiles para el futuro) ---

    public List<Recurso> buscarPorGrupo(Long grupoId) {
        if (grupoId == null) return new ArrayList<>();
        return recursoRepository.findByGrupo_Id(grupoId);
    }

    public List<Recurso> buscarPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) return new ArrayList<>();
        return recursoRepository.findByAutor(autor);
    }

    public List<Recurso> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) return new ArrayList<>();
        return recursoRepository.findByTipo(tipo);
    }
    
    /**
     * Obtener estadísticas simples
     */
    public String obtenerEstadisticas() {
        long total = listarTodos().size();
        return "Total de recursos: " + total;
    }
}