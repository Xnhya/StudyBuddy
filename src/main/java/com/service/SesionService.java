package com.service;

import com.model.Sesion;
import com.dto.SesionListadoDTO;
import com.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestión de sesiones de estudio con persistencia en base de datos
 */
@Service
@Transactional
public class SesionService {
    @Autowired
    private SesionRepository sesionRepository;
    
    public Sesion add(Sesion s) {
        if (s != null) {
            return sesionRepository.save(s);
        }
        return null;
    }
    
    public List<Sesion> listar() {
        return sesionRepository.findAll();
    }
    
    /**
     * Listar sesiones como DTO con datos de grupo y usuario
     */
    public List<SesionListadoDTO> listarDTO() {
        return sesionRepository.findAllSesionesListado();
    }
    
    public Sesion buscarPorId(Long id) {
        if (id == null) return null;
        return sesionRepository.findById(id).orElse(null);
    }
    
    /**
     * Actualizar una sesión existente
     */
    public Sesion actualizar(Sesion sesion) {
        if (sesion != null && sesion.getId() != null) {
            if (sesionRepository.existsById(sesion.getId())) {
                return sesionRepository.save(sesion);
            }
        }
        return null;
    }
    
    public List<Sesion> buscarPorGrupo(Long grupoId) {
        if (grupoId == null) return new ArrayList<>();
        return sesionRepository.findByGrupo_Id(grupoId);
    }
    
    public List<Sesion> buscarPorUsuario(Long usuarioId) {
        if (usuarioId == null) return new ArrayList<>();
        return sesionRepository.findByUsuario_Id(usuarioId);
    }
    
    public void eliminar(Long id) {
        if (id != null) {
            sesionRepository.deleteById(id);
        }
    }
}
