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
}