package com.service;

import com.model.Sesion;
import com.repository.SesionRepository; // <-- CAMBIO
import org.springframework.beans.factory.annotation.Autowired; // <-- CAMBIO
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- CAMBIO

import java.util.List;

@Service
public class SesionService {
    
    @Autowired
    private SesionRepository sesionRepository; // <-- CAMBIO

    @Transactional // <-- CAMBIO
    public synchronized Sesion add(Sesion s) {
        return sesionRepository.save(s);
    }

    public synchronized List<Sesion> listar() {
        return sesionRepository.findAll();
    }

    public synchronized Sesion buscarPorId(Integer id) {
        if (id == null) return null;
        return sesionRepository.findById(id).orElse(null);
    }

    @Transactional
    public synchronized Sesion actualizar(Sesion sesion) {
        if (sesion != null && sesion.getId() != null && sesionRepository.existsById(sesion.getId())) {
            return sesionRepository.save(sesion);
        }
        return null;
    }

    @Transactional
    public synchronized boolean eliminar(Integer id) {
        if (id == null) return false;
        if (sesionRepository.existsById(id)) {
            sesionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}