package com.service;

import com.model.Recurso;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Servicio para gestión de recursos en memoria
 * Implementa operaciones CRUD para recursos de estudio
 */
@Service
public class RecursoService {
    private final List<Recurso> recursos = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Agregar un nuevo recurso
     */
    public synchronized Recurso agregar(Recurso recurso) {
        if (recurso != null) {
            recurso.setId(idGenerator.getAndIncrement());
            recursos.add(recurso);
            return recurso;
        }
        return null;
    }

    /**
     * Listar todos los recursos
     */
    public synchronized List<Recurso> listar() {
        return new ArrayList<>(recursos);
    }

    /**
     * Buscar recurso por ID
     */
    public synchronized Recurso buscarPorId(Long id) {
        if (id == null) return null;
        return recursos.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Buscar recursos por grupo
     */
    public synchronized List<Recurso> buscarPorGrupo(Long grupoId) {
        if (grupoId == null) return new ArrayList<>();
        return recursos.stream()
                .filter(r -> r.getGrupoId().equals(grupoId))
                .toList();
    }

    /**
     * Buscar recursos por autor
     */
    public synchronized List<Recurso> buscarPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) return new ArrayList<>();
        return recursos.stream()
                .filter(r -> autor.equalsIgnoreCase(r.getAutor()))
                .toList();
    }

    /**
     * Buscar recursos por tipo
     */
    public synchronized List<Recurso> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) return new ArrayList<>();
        return recursos.stream()
                .filter(r -> tipo.equalsIgnoreCase(r.getTipo()))
                .toList();
    }

    /**
     * Actualizar un recurso existente
     */
    public synchronized Recurso actualizar(Recurso recurso) {
        if (recurso != null && recurso.getId() != null) {
            for (int i = 0; i < recursos.size(); i++) {
                if (recursos.get(i).getId().equals(recurso.getId())) {
                    recursos.set(i, recurso);
                    return recurso;
                }
            }
        }
        return null;
    }

    /**
     * Eliminar recurso por ID
     */
    public synchronized boolean eliminar(Long id) {
        if (id == null) return false;
        return recursos.removeIf(r -> r.getId().equals(id));
    }

    /**
     * Eliminar todos los recursos de un grupo
     */
    public synchronized int eliminarPorGrupo(Long grupoId) {
        if (grupoId == null) return 0;
        int initialSize = recursos.size();
        recursos.removeIf(r -> r.getGrupoId().equals(grupoId));
        return initialSize - recursos.size();
    }

    /**
     * Contar recursos por tipo
     */
    public synchronized long contarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) return 0;
        return recursos.stream()
                .filter(r -> tipo.equalsIgnoreCase(r.getTipo()))
                .count();
    }

    /**
     * Obtener estadísticas de recursos
     */
    public synchronized String obtenerEstadisticas() {
        long total = recursos.size();
        long documentos = contarPorTipo("DOCUMENTO");
        long enlaces = contarPorTipo("ENLACE");
        long videos = contarPorTipo("VIDEO");
        long imagenes = contarPorTipo("IMAGEN");
        
        return String.format("Total: %d | Documentos: %d | Enlaces: %d | Videos: %d | Imágenes: %d", 
                total, documentos, enlaces, videos, imagenes);
    }
}
