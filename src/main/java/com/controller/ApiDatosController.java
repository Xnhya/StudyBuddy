package com.controller;

import com.model.Carrera;
import com.model.Facultad;
import com.repository.CarreraRepository;
import com.repository.FacultadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para proporcionar datos para desplegables en cascada
 */
@RestController
@RequestMapping("/api")
public class ApiDatosController {
    
    @Autowired
    private FacultadRepository facultadRepository;
    
    @Autowired
    private CarreraRepository carreraRepository;
    
    /**
     * Obtener todas las facultades
     */
    @GetMapping("/facultades")
    public List<Facultad> obtenerFacultades() {
        return facultadRepository.findAll();
    }
    
    /**
     * Obtener carreras por facultad
     */
    @GetMapping("/carreras/{idFacultad}")
    public List<Carrera> obtenerCarrerasPorFacultad(@PathVariable Long idFacultad) {
        return carreraRepository.findByFacultadIdFacultad(idFacultad);
    }
}
