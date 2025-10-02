package com.service;

import com.model.Grupo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GrupoService {
    private final List<Grupo> grupos = new ArrayList<>();
    
    /**
     * Inicializar grupos de prueba
     */
    public GrupoService() {
        inicializarGruposPrueba();
    }
    
    /**
     * Inicializar grupos de prueba para la demo
     */
    private void inicializarGruposPrueba() {
        Grupo grupo1 = new Grupo("Estudiantes de Cálculo", "Cálculo", "Tarde", "Grupo para estudiar cálculo diferencial e integral");
        grupo1.addMiembro("María García");
        grupos.add(grupo1);
        
        Grupo grupo2 = new Grupo("Programadores Java", "Programación", "Noche", "Estudiamos Java y Spring Boot");
        grupo2.addMiembro("Carlos López");
        grupo2.addMiembro("María García");
        grupos.add(grupo2);
        
        Grupo grupo3 = new Grupo("Física Cuántica", "Física", "Mañana", "Grupo avanzado de física cuántica");
        grupo3.addMiembro("María García");
        grupos.add(grupo3);
    }

    public synchronized void add(Grupo g) {
        // evitar duplicados por nombre (simple)
        if (buscar(g.getNombre()) == null) {
            grupos.add(g);
        }
    }

    public synchronized List<Grupo> listar() {
        return new ArrayList<>(grupos);
    }

    public synchronized Grupo buscar(String nombre) {
        if (nombre == null) return null;
        return grupos.stream()
                .filter(g -> g.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
    }

    public synchronized void unirse(String nombre, String usuario) {
        Grupo g = buscar(nombre);
        if (g != null && usuario != null) {
            g.addMiembro(usuario);
        }
    }

    public synchronized void eliminar(String nombre) {
        grupos.removeIf(g -> g.getNombre().equalsIgnoreCase(nombre));
    }
}
