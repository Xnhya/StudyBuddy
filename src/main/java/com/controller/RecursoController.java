package com.controller;

import com.model.Grupo;
import com.model.Recurso;
import com.model.Usuario;
import com.service.GrupoService;
import com.service.RecursoService;
import com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // IMPORTANTE: Para saber quién está logueado
import java.util.List;

/**
 * Controlador para gestión de recursos de estudio
 * (VERSIÓN COMPLETA Y SEGURA con Spring Security - Principal)
 */
@Controller
@RequestMapping("/recursos")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private GrupoService grupoService;

    /**
     * Método auxiliar para obtener el usuario COMPLETO desde la BD
     * usando el 'Principal' (que solo contiene el email)
     */
    private Usuario getUsuarioLogueado(Principal principal) {
        if (principal == null) {
            return null;
        }
        String email = principal.getName(); // Obtenemos el email del usuario logueado
        return usuarioService.buscarPorEmail(email); // Buscamos el objeto completo
    }

    /**
     * READ (Listar): Listar todos los recursos
     */
    @GetMapping
    public String listarRecursos(Model model, Principal principal) {
        model.addAttribute("recursos", recursoService.listarTodos());
        model.addAttribute("usuario", getUsuarioLogueado(principal));
        model.addAttribute("active", "recursos");
        return "recursos"; // Vista: recursos.html
    }

    /**
     * CREATE (Formulario): Mostrar formulario para crear nuevo recurso
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(@RequestParam(required = false) Long grupoId, Model model, Principal principal) {
        Recurso recurso = new Recurso();
        
        if (grupoId != null) {
            Grupo grupo = grupoService.buscarPorId(grupoId);
            if (grupo != null) {
                recurso.setGrupo(grupo);
            }
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", getUsuarioLogueado(principal));
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        model.addAttribute("grupos", grupoService.listar());
        
        return "recurso-form"; // Vista: recurso-form.html
    }

    /**
     * CREATE (Guardar): Procesar el formulario de creación
     */
    @PostMapping("/crear")
    public String crearRecurso(@ModelAttribute Recurso recurso, Model model, Principal principal) {
        Usuario usuarioActual = getUsuarioLogueado(principal);
        
        if (usuarioActual != null) {
            // Asignamos el nombre completo del usuario como autor
            recurso.setAutor(usuarioActual.getNombre() + " " + usuarioActual.getApellido());
        }
        
        recursoService.guardar(recurso);
        return "redirect:/recursos";
    }

    /**
     * READ (Detalle): Ver detalles de un recurso
     */
    @GetMapping("/{id}")
    public String verRecurso(@PathVariable Long id, Model model, Principal principal) {
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", getUsuarioLogueado(principal));
        return "recurso-detalle"; // Vista: recurso-detalle.html
    }

    /**
     * UPDATE (Formulario): Mostrar formulario para editar un recurso
     */
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, Principal principal) {
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", getUsuarioLogueado(principal));
        model.addAttribute("grupos", grupoService.listar());
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        
        return "recurso-form"; // Reutilizamos la misma vista del formulario
    }

    /**
     * UPDATE (Guardar): Procesar el formulario de edición
     */
    @PostMapping("/{id}/editar")
    public String actualizarRecurso(@PathVariable Long id, @ModelAttribute Recurso recursoForm) {
        
        // Buscamos el recurso original para no perder datos (como el autor)
        Recurso recursoOriginal = recursoService.buscarPorId(id);
        if (recursoOriginal == null) {
            return "redirect:/recursos";
        }
        
        // Actualizamos solo los campos que vienen del formulario
        recursoOriginal.setTitulo(recursoForm.getTitulo());
        recursoOriginal.setDescripcion(recursoForm.getDescripcion());
        recursoOriginal.setTipo(recursoForm.getTipo());
        recursoOriginal.setGrupo(recursoForm.getGrupo()); // Spring bindea el ID del grupo
        
        // Guardamos el objeto original actualizado
        recursoService.guardar(recursoOriginal); 
        
        return "redirect:/recursos/" + id; // Redirigimos al detalle
    }

    /**
     * DELETE: Eliminar un recurso
     */
    @GetMapping("/{id}/eliminar")
    public String eliminarRecurso(@PathVariable Long id) {
        recursoService.eliminar(id);
        return "redirect:/recursos";
    }
}