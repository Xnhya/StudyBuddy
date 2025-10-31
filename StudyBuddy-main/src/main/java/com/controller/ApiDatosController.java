package com.controller;

import com.model.Carrera;
import com.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Este controlador es una API REST.
 * No devuelve HTML, devuelve datos puros (JSON) para que JavaScript los use.
 * Lo usaremos para las listas desplegables en cascada.
 */
@RestController
@RequestMapping("/api") // Todas las URLs aquí empezarán con /api
public class ApiDatosController {

    @Autowired
    private CarreraRepository carreraRepository;

    /**
     * Endpoint para obtener las carreras de una facultad específica.
     * Ej: /api/carreras/1 (Devolverá las carreras de Ingeniería)
     */
    @GetMapping("/carreras/{idFacultad}")
    public List<Carrera> getCarrerasPorFacultad(@PathVariable Integer idFacultad) {
        // Usamos el método mágico que ya tenías en tu repositorio
        return carreraRepository.findByFacultadIdFacultad(idFacultad);
    }
}