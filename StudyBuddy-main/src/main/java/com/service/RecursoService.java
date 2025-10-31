package com.service;

import com.model.Recurso;
import com.repository.RecursoRepository; // <-- CAMBIO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- CAMBIO

import java.util.List;

/**
 * Servicio para gestión de recursos (AHORA CON BASE DE DATOS)
 */
@Service
@Transactional(readOnly = true) // <-- CAMBIO
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository; // <-- CAMBIO

    /**
     * Agregar un nuevo recurso
     */
    @Transactional
    public synchronized Recurso agregar(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    /**
     * Listar todos los recursos
     */
    public synchronized List<Recurso> listar() {
        return recursoRepository.findAll();
    }

    /**
     * Buscar recurso por ID
     */
    public synchronized Recurso buscarPorId(Integer id) { // <-- CAMBIO: Long -> Integer
        if (id == null) return null;
        return recursoRepository.findById(id).orElse(null);
    }

    /**
     * Buscar recursos por grupo
     */
    public synchronized List<Recurso> buscarPorGrupo(Integer grupoId) { // <-- CAMBIO: Long -> Integer
        if (grupoId == null) return List.of();
        // Usamos el método mágico del repositorio
        return recursoRepository.findByGrupo_Id(grupoId);
    }

    /**
     * Buscar recursos por autor
     */
    public synchronized List<Recurso> buscarPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) return List.of();
        return recursoRepository.findByAutor(autor);
    }

    /**
     * Buscar recursos por tipo
     */
    public synchronized List<Recurso> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) return List.of();
        return recursoRepository.findByTipo(tipo);
    }

    /**
     * Actualizar un recurso existente
     */
    @Transactional
    public synchronized Recurso actualizar(Recurso recurso) {
        if (recurso != null && recurso.getId() != null) {
            if (recursoRepository.existsById(recurso.getId())) {
                return recursoRepository.save(recurso);
            }
        }
        return null;
    }

    /**
     * Eliminar recurso por ID
     */
    @Transactional
    public synchronized boolean eliminar(Integer id) { // <-- CAMBIO: Long -> Integer
        if (id == null) return false;
        if (recursoRepository.existsById(id)) {
            recursoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Eliminar todos los recursos de un grupo
     */
    @Transactional
    public synchronized int eliminarPorGrupo(Integer grupoId) { // <-- CAMBIO: Long -> Integer
        if (grupoId == null) return 0;
        List<Recurso> recursos = recursoRepository.findByGrupo_Id(grupoId);
        if (!recursos.isEmpty()) {
            recursoRepository.deleteAll(recursos);
            return recursos.size();
        }
        return 0;
    }
    
    // ... (Puedes borrar los métodos 'contarPorTipo' y 'obtenerEstadisticas'
    // o re-implementarlos con 'recursoRepository.count(...)' si los necesitas)
}