package com.controller;

import com.model.GrupoEstudio; // <-- CAMBIO
import com.model.Recurso;
import com.model.Usuario;
import com.service.GrupoService; // <-- CAMBIO
import com.service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recursos")
@SessionAttributes("usuarioLogueado")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;
    
    @Autowired
    private GrupoService grupoService; // <-- CAMBIO: Necesario para buscar el grupo

    @ModelAttribute("usuarioLogueado")
    public Usuario getUsuarioLogueado() {
        return null;
    }

    @GetMapping
    public String listarRecursos(Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        model.addAttribute("recursos", recursoService.listar());
        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("active", "recursos");
        return "recursos";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(@RequestParam(required = false) Integer grupoId, Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) { // <-- CAMBIO: Long -> Integer
        Recurso recurso = new Recurso();
        if (grupoId != null) {
            // --- CAMBIO CLAVE ---
            // Buscamos el objeto GrupoEstudio completo y lo asignamos
            GrupoEstudio grupo = grupoService.buscarPorId(grupoId);
            recurso.setGrupo(grupo);
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        return "recurso-form";
    }

    @PostMapping("/crear")
    public String crearRecurso(@ModelAttribute Recurso recurso, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        recurso.setAutor(usuarioLogueado.getNombre());
        
        // --- CAMBIO SUTIL ---
        // Si el 'recurso' viene del form con el 'grupo.id' seteado,
        // Spring Data JPA es lo bastante inteligente para manejarlo 
        // si el formulario envía 'grupo.id' como un input hidden.
        // Pero 'mostrarFormularioCrear' ya seteó el objeto 'grupo'
        // y el 'recurso' en el modelo.
        
        // Si el 'recurso' del @ModelAttribute no trae el objeto 'grupo'
        // (porque el form solo envió 'grupo.id'), necesitaríamos recargarlo.
        // Asumiremos que el data-binding funciona o que el 'recurso'
        // de la sesión de formulario aún tiene el objeto 'grupo' seteado.
        
        recursoService.agregar(recurso);
        
        // Redirigir a los detalles del grupo sería mejor
        if (recurso.getGrupo() != null) {
            return "redirect:/grupos/ver/" + recurso.getGrupo().getId();
        }
        return "redirect:/recursos";
    }

    @GetMapping("/{id}")
    public String verRecurso(@PathVariable Integer id, Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) { // <-- CAMBIO: Long -> Integer
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", usuarioLogueado);
        return "recurso-detalle"; // Asumo que tienes una vista 'recurso-detalle.html'
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) { // <-- CAMBIO: Long -> Integer
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        return "recurso-form";
    }

    @PostMapping("/{id}/editar")
    public String actualizarRecurso(@PathVariable Integer id, @ModelAttribute Recurso recurso) { // <-- CAMBIO: Long -> Integer
        recurso.setId(id);
        
        // --- CAMBIO CLAVE ---
        // Debemos re-asignar el grupo si no viene del formulario
        Recurso recursoExistente = recursoService.buscarPorId(id);
        if (recursoExistente != null) {
            recurso.setGrupo(recursoExistente.getGrupo());
        }
        
        recursoService.actualizar(recurso);
        return "redirect:/recursos/" + id;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarRecurso(@PathVariable Integer id) { // <-- CAMBIO: Long -> Integer
        recursoService.eliminar(id);
        return "redirect:/recursos";
    }

    // ... (El resto de métodos de búsqueda por autor/tipo funcionan igual)
    @GetMapping("/buscar")
    public String buscarRecursos(@RequestParam(required = false) String tipo, Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        List<Recurso> recursos;
        if (tipo != null && !tipo.trim().isEmpty()) {
            recursos = recursoService.buscarPorTipo(tipo);
        } else {
            recursos = recursoService.listar();
        }
        
        model.addAttribute("recursos", recursos);
        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("tipoSeleccionado", tipo);
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        return "recursos";
    }

    @GetMapping("/autor/{autor}")
    public String buscarPorAutor(@PathVariable String autor, Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        model.addAttribute("recursos", recursoService.buscarPorAutor(autor));
        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("autorSeleccionado", autor);
        return "recursos";
    }
}