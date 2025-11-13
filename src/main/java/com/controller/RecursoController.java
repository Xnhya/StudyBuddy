package com.controller;

import com.model.Recurso;
import com.model.Usuario;
import com.service.RecursoService;
import com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestión de recursos de estudio
 * Maneja las operaciones CRUD de recursos asociados a grupos
 */
@Controller
@RequestMapping("/recursos")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Listar todos los recursos
     */
    @GetMapping
    public String listarRecursos(Model model) {
        model.addAttribute("recursos", recursoService.listar());
        model.addAttribute("usuario", usuarioService.obtener());
        model.addAttribute("active", "recursos");
        return "recursos";
    }

    /**
     * Mostrar formulario para crear nuevo recurso
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(@RequestParam(required = false) Long grupoId, Model model) {
        Recurso recurso = new Recurso();
        if (grupoId != null) {
            recurso.setGrupoId(grupoId);
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", usuarioService.obtener());
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        return "recurso-form";
    }

    /**
     * Crear nuevo recurso
     */
    @PostMapping("/crear")
    public String crearRecurso(@ModelAttribute Recurso recurso, Model model) {
        Usuario usuarioActual = usuarioService.obtener();
        if (usuarioActual != null) {
            recurso.setAutor(usuarioActual.getNombre());
        }
        
        recursoService.agregar(recurso);
        return "redirect:/recursos";
    }

    /**
     * Ver detalles de un recurso
     */
    @GetMapping("/{id}")
    public String verRecurso(@PathVariable Long id, Model model) {
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", usuarioService.obtener());
        return "recurso-detalle";
    }

    /**
     * Mostrar formulario para editar recurso
     */
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", usuarioService.obtener());
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        return "recurso-form";
    }

    /**
     * Actualizar recurso existente
     */
    @PostMapping("/{id}/editar")
    public String actualizarRecurso(@PathVariable Long id, @ModelAttribute Recurso recurso) {
        recurso.setId(id);
        recursoService.actualizar(recurso);
        return "redirect:/recursos/" + id;
    }

    /**
     * Eliminar recurso
     */
    @PostMapping("/{id}/eliminar")
    public String eliminarRecurso(@PathVariable Long id) {
        recursoService.eliminar(id);
        return "redirect:/recursos";
    }

    /**
     * Buscar recursos por tipo
     */
    @GetMapping("/buscar")
    public String buscarRecursos(@RequestParam(required = false) String tipo, Model model) {
        List<Recurso> recursos;
        if (tipo != null && !tipo.trim().isEmpty()) {
            recursos = recursoService.buscarPorTipo(tipo);
        } else {
            recursos = recursoService.listar();
        }
        
        model.addAttribute("recursos", recursos);
        model.addAttribute("usuario", usuarioService.obtener());
        model.addAttribute("tipoSeleccionado", tipo);
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        return "recursos";
    }

    /**
     * Buscar recursos por autor
     */
    @GetMapping("/autor/{autor}")
    public String buscarPorAutor(@PathVariable String autor, Model model) {
        model.addAttribute("recursos", recursoService.buscarPorAutor(autor));
        model.addAttribute("usuario", usuarioService.obtener());
        model.addAttribute("autorSeleccionado", autor);
        return "recursos";
    }
}
