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
 * Implementa operaciones CRUD para recursos de estudio
 */
@Service
@Transactional
public class RecursoService {
    @Autowired
    private RecursoRepository recursoRepository;
    
    /**
     * Agregar un nuevo recurso
     */
    public Recurso agregar(Recurso recurso) {
        if (recurso != null) {
            return recursoRepository.save(recurso);
        }
        return null;
    }
    
    /**
     * Listar todos los recursos
     */
    public List<Recurso> listar() {
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
     * Buscar recursos por grupo
     */
    public List<Recurso> buscarPorGrupo(Long grupoId) {
        if (grupoId == null) return new ArrayList<>();
        return recursoRepository.findByGrupo_Id(grupoId);
    }
    
    /**
     * Buscar recursos por autor
     */
    public List<Recurso> buscarPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) return new ArrayList<>();
        return recursoRepository.findByAutor(autor);
    }
    
    /**
     * Buscar recursos por tipo
     */
    public List<Recurso> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) return new ArrayList<>();
        return recursoRepository.findByTipo(tipo);
    }
    
    /**
     * Actualizar un recurso existente
     */
    public Recurso actualizar(Recurso recurso) {
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
    public boolean eliminar(Long id) {
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
    public int eliminarPorGrupo(Long grupoId) {
        if (grupoId == null) return 0;
        List<Recurso> recursos = buscarPorGrupo(grupoId);
        int cantidad = recursos.size();
        recursoRepository.deleteAll(recursos);
        return cantidad;
    }
    
    /**
     * Contar recursos por tipo
     */
    public long contarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) return 0;
        return buscarPorTipo(tipo).size();
    }
    
    /**
     * Obtener estadísticas de recursos
     */
    public String obtenerEstadisticas() {
        long total = listar().size();
        long documentos = contarPorTipo("DOCUMENTO");
        long enlaces = contarPorTipo("ENLACE");
        long videos = contarPorTipo("VIDEO");
        long imagenes = contarPorTipo("IMAGEN");
        
        return String.format("Total: %d | Documentos: %d | Enlaces: %d | Videos: %d | Imágenes: %d", 
                total, documentos, enlaces, videos, imagenes);
    }
}
