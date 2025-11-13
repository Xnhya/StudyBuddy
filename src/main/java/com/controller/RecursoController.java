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

import java.security.Principal; // IMPORTANTE: Para seguridad
import java.util.List;

@Controller
@RequestMapping("/recursos")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoService grupoService; // Necesario para vincular recursos a grupos

    // --- MÉTODO AUXILIAR PARA OBTENER EL USUARIO LOGUEADO ---
    // Evita repetir código en cada método
    private Usuario getUsuarioLogueado(Principal principal) {
        if (principal != null) {
            return usuarioService.buscarPorEmail(principal.getName());
        }
        return null;
    }

    /**
     * Listar todos los recursos
     */
    @GetMapping
    public String listarRecursos(Model model, Principal principal) {
        model.addAttribute("recursos", recursoService.listarTodos()); // Asegúrate que este método exista en tu Service
        model.addAttribute("usuario", getUsuarioLogueado(principal));
        model.addAttribute("active", "recursos");
        return "recursos";
    }

    /**
     * Mostrar formulario para crear nuevo recurso
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(@RequestParam(required = false) Long grupoId, Model model, Principal principal) {
        Recurso recurso = new Recurso();
        
        // Si venimos desde un grupo específico, lo pre-cargamos
        if (grupoId != null) {
            Grupo grupo = grupoService.buscarPorId(grupoId); // Asegúrate que GrupoService tenga este método
            if (grupo != null) {
                recurso.setGrupo(grupo);
            }
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", getUsuarioLogueado(principal));
        model.addAttribute("tiposRecurso", List.of("DOCUMENTO", "ENLACE", "VIDEO", "IMAGEN", "AUDIO"));
        
        // Pasamos la lista de grupos por si quiere cambiarlo en el select
        model.addAttribute("grupos", grupoService.listar()); 
        
        return "recurso-form";
    }

    /**
     * Crear nuevo recurso
     */
    @PostMapping("/crear")
    public String crearRecurso(@ModelAttribute Recurso recurso, Principal principal) {
        Usuario usuarioActual = getUsuarioLogueado(principal);
        
        if (usuarioActual != null) {
            // Guardamos el nombre del autor (o podrías guardar el objeto Usuario completo si tu BD lo permite)
            recurso.setAutor(usuarioActual.getNombre() + " " + usuarioActual.getApellido());
        }
        
        recursoService.guardar(recurso);
        
        // Si el recurso pertenece a un grupo, volvemos al detalle del grupo, si no, a la lista general
        if (recurso.getGrupo() != null) {
            return "redirect:/grupos/" + recurso.getGrupo().getId();
        }
        return "redirect:/recursos";
    }

    /**
     * Ver detalles de un recurso
     */
    @GetMapping("/{id}")
    public String verRecurso(@PathVariable Long id, Model model, Principal principal) {
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        
        model.addAttribute("recurso", recurso);
        model.addAttribute("usuario", getUsuarioLogueado(principal));
        return "recurso-detalle"; // Asegúrate de tener esta vista HTML
    }

    /**
     * Mostrar formulario para editar
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
        return "recurso-form";
    }

    /**
     * Actualizar recurso
     */
    @PostMapping("/{id}/editar")
    public String actualizarRecurso(@PathVariable Long id, @ModelAttribute Recurso recurso) {
        // Recuperamos el recurso original para no perder datos como el autor o fecha
        Recurso recursoOriginal = recursoService.buscarPorId(id);
        if (recursoOriginal != null) {
            recurso.setId(id);
            recurso.setAutor(recursoOriginal.getAutor()); // Mantenemos el autor original
            recursoService.guardar(recurso);
        }
        return "redirect:/recursos";
    }

    /**
     * Eliminar recurso
     */
    @GetMapping("/{id}/eliminar") // Cambiado a GetMapping para simplificar enlaces directos (aunque Post es más correcto REST)
    public String eliminarRecurso(@PathVariable Long id) {
        recursoService.eliminar(id);
        return "redirect:/recursos";
    }

    // ... Puedes mantener los métodos de búsqueda si implementas la lógica en el repositorio ...
}