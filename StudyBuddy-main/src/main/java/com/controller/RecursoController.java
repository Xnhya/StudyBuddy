package com.controller;

import com.model.Recurso;
import com.model.Usuario;
import com.model.GrupoEstudio; // Importar
import com.service.GrupoService; // Importar
import com.service.RecursoService;
import com.service.UsuarioService; // Importar
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal; // <-- CAMBIO: Importar Principal
import java.util.List;

/**
 * Controlador para gestión de recursos de estudio
 * ¡CORREGIDO! Ahora usa Spring Security (Principal)
 */
@Controller
@RequestMapping("/recursos")
// --- ¡CAMBIO CRÍTICO! ---
// @SessionAttributes("usuarioLogueado") // <-- ELIMINADO
public class RecursoController {

    @Autowired
    private RecursoService recursoService;
    
    @Autowired
    private UsuarioService usuarioService; // <-- AÑADIDO
    
    @Autowired
    private GrupoService grupoService;

    /**
     * ¡CAMBIO! Método de ayuda para obtener el
     * usuario de nuestra BDD a partir del 'Principal' de Spring Security.
     */
    private Usuario getUsuarioActual(Principal principal) {
        if (principal == null) {
            return null;
        }
        return usuarioService.buscarPorEmail(principal.getName());
    }

    // --- ¡CAMBIO! @ModelAttribute("usuarioLogueado") ELIMINADO ---

    /**
     * Listar todos los recursos
     */
    @GetMapping
    public String listarRecursos(Model model, Principal principal) { // <-- CAMBIO
        model.addAttribute("recursos", recursoService.listar());
        model.addAttribute("usuario", getUsuarioActual(principal)); // <-- CAMBIO
        model.addAttribute("active", "recursos");
        return "recursos"; // Asumo que tienes un 'recursos.html'
    }

    /**
     * Mostrar formulario para crear nuevo recurso
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(@RequestParam(required = false) Integer grupoId, Model model, Principal principal) { // <-- CAMBIO
        Recurso recurso = new Recurso();
        if (grupoId != null) {
            GrupoEstudio grupo = grupoService.buscarPorId(grupoId);
            recurso.setGrupo(grupo);
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", getUsuarioActual(principal)); // <-- CAMBIO
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        return "recurso-form"; // Asumo que tienes un 'recurso-form.html'
    }

    /**
     * Crear nuevo recurso
     */
    @PostMapping("/crear")
    public String crearRecurso(@ModelAttribute Recurso recurso, Principal principal) { // <-- CAMBIO
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null) {
            return "redirect:/login"; // Proteger
        }
        recurso.setAutor(usuarioLogueado.getNombre());
        
        recursoService.agregar(recurso);
        
        if (recurso.getGrupo() != null) {
            return "redirect:/grupos/ver/" + recurso.getGrupo().getId();
        }
        return "redirect:/recursos";
    }

    /**
     * Ver detalles de un recurso
     */
    @GetMapping("/{id}")
    public String verRecurso(@PathVariable Integer id, Model model, Principal principal) { // <-- CAMBIO
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", getUsuarioActual(principal)); // <-- CAMBIO
        return "recurso-detalle"; // Asumo que tienes 'recurso-detalle.html'
    }

    /**
     * Mostrar formulario para editar recurso
     */
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model, Principal principal) { // <-- CAMBIO
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", getUsuarioActual(principal)); // <-- CAMBIO
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        return "recurso-form";
    }

    /**
     * Actualizar recurso existente
     */
    @PostMapping("/{id}/editar")
    public String actualizarRecurso(@PathVariable Integer id, @ModelAttribute Recurso recurso, Principal principal) { // <-- CAMBIO
        if (getUsuarioActual(principal) == null) return "redirect:/login"; // Proteger

        recurso.setId(id);
        Recurso recursoExistente = recursoService.buscarPorId(id);
        if (recursoExistente != null) {
            recurso.setGrupo(recursoExistente.getGrupo()); // Preservar el grupo
        }
        
        recursoService.actualizar(recurso);
        return "redirect:/recursos/" + id;
    }

    /**
     * Eliminar recurso
     */
    @PostMapping("/{id}/eliminar")
    public String eliminarRecurso(@PathVariable Integer id, Principal principal) { // <-- CAMBIO
        if (getUsuarioActual(principal) == null) return "redirect:/login"; // Proteger
        
        recursoService.eliminar(id);
        return "redirect:/recursos";
    }

    // ... (Los métodos de búsqueda no necesitan 'principal' a menos que quieras protegerlos) ...
    // Dejaré los métodos 'buscar' y 'autor' como estaban, pero puedes 
    // añadir 'Principal principal' y 'model.addAttribute("usuario", ...)'
    // si esas páginas usan el navbar.
}