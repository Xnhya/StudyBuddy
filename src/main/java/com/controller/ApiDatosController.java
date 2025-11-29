package com.controller;

import com.repository.CarreraRepository;
import com.repository.FacultadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para proporcionar datos para desplegables en cascada (vía DTOs ligeros)
 */
@RestController
@RequestMapping("/api")
public class ApiDatosController {
    
    @Autowired
    private FacultadRepository facultadRepository;
    
    @Autowired
    private CarreraRepository carreraRepository;

    public static class OpcionDTO {
        public Long id;
        public String nombre;
        public OpcionDTO(Long id, String nombre) { this.id = id; this.nombre = nombre; }
    }
    
    /**
     * Obtener todas las facultades (id, nombre)
     */
    @GetMapping("/facultades")
    public List<OpcionDTO> obtenerFacultades() {
        return facultadRepository.findAll().stream()
                .map(f -> new OpcionDTO(f.getIdFacultad(), f.getNombre()))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener carreras por facultad (id, nombre)
     */
    @GetMapping("/carreras/{idFacultad}")
    public List<OpcionDTO> obtenerCarrerasPorFacultad(@PathVariable Long idFacultad) {
        return carreraRepository.findByFacultadIdFacultad(idFacultad).stream()
                .map(c -> new OpcionDTO(c.getIdCarrera(), c.getNombre()))
                .collect(Collectors.toList());
    }
}
