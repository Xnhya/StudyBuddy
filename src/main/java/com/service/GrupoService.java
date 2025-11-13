package com.service;

import com.model.Grupo;
import com.model.Usuario;
import com.dto.GrupoListadoDTO;
import com.repository.GrupoRepository;
import com.repository.UsuarioRepository;
import com.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestión de grupos de estudio con persistencia en base de datos
 */
@Service
@Transactional
public class GrupoService {
    @Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private MateriaRepository materiaRepository;
    
    public void add(Grupo g) {
        if (g != null && buscar(g.getNombre()) == null) {
            grupoRepository.save(g);
        }
    }
    
    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }
    
    /**
     * Listar grupos usando DTOs para mejor rendimiento
     */
    public List<GrupoListadoDTO> listarDTO() {
        return grupoRepository.findAllGruposListado();
    }
    
    public Grupo buscar(String nombre) {
        if (nombre == null) return null;
        return grupoRepository.findByNombre(nombre);
    }
    
    public Grupo buscarPorId(Long id) {
        if (id == null) return null;
        return grupoRepository.findById(id).orElse(null);
    }
    
    public void unirse(String nombreGrupo, String nombreUsuario) {
        Grupo grupo = buscar(nombreGrupo);
        Usuario usuario = usuarioRepository.findByNombre(nombreUsuario);
        
        if (grupo != null && usuario != null) {
            grupo.addMiembro(usuario);
            grupoRepository.save(grupo);
        }
    }
    
    public void unirsePorId(Long idGrupo, Long idUsuario) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElse(null);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        
        if (grupo != null && usuario != null) {
            grupo.addMiembro(usuario);
            grupoRepository.save(grupo);
        }
    }
    
    public void eliminar(String nombre) {
        Grupo grupo = buscar(nombre);
        if (grupo != null) {
            grupoRepository.delete(grupo);
        }
    }
    
    public void eliminarPorId(Long id) {
        if (id != null) {
            grupoRepository.deleteById(id);
        }
    }
}
