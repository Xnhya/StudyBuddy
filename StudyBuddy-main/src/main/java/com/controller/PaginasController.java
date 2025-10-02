package com.controller;

import com.model.Grupo;
import com.model.Sesion;
import com.model.Usuario;
import com.service.GrupoService;
import com.service.SesionService;
import com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PaginasController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SesionService sesionService;

    @GetMapping({"/", "/landing"})
    public String landing(Model model) {
        model.addAttribute("active", "landing");
        model.addAttribute("usuario", usuarioService.obtener());
        return "landing";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("usuario", usuarioService.obtener());
        return "login";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        model.addAttribute("active", "buscar");
        model.addAttribute("grupos", grupoService.listar());
        model.addAttribute("usuario", usuarioService.obtener());
        return "buscar";
    }

    @PostMapping("/buscar/crear")
    public String crearGrupo(@ModelAttribute Grupo grupo) {
        grupoService.add(grupo);
        return "redirect:/buscar";
    }

    @PostMapping("/grupos/unirse")
    public String unirseGrupo(@RequestParam("nombre") String nombre) {
        var u = usuarioService.obtener();
        String nombreUsuario = (u != null && u.getNombre() != null) ? u.getNombre() : "Anónimo";
        grupoService.unirse(nombre, nombreUsuario);
        return "redirect:/buscar";
    }

    @GetMapping("/grupos/ver/{nombre}")
    public String verGrupo(@PathVariable String nombre, Model model) {
        Grupo g = grupoService.buscar(nombre);
        model.addAttribute("grupo", g);
        model.addAttribute("usuario", usuarioService.obtener());
        model.addAttribute("active", "buscar");
        return "grupo-detalle";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("active", "dashboard");
        model.addAttribute("grupos", grupoService.listar());
        model.addAttribute("usuario", usuarioService.obtener());
        return "dashboard";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        model.addAttribute("active", "perfil");
        // si ya hay usuario, pasar al form para editar; si no, nuevo
        Usuario u = usuarioService.obtener();
        model.addAttribute("usuarioForm", u != null ? u : new Usuario());
        model.addAttribute("usuario", u);
        return "perfil";
    }

    @PostMapping("/perfil")
    public String guardarPerfil(@ModelAttribute("usuarioForm") Usuario usuario,
                                @RequestParam(value = "materias", required = false) List<String> materias,
                                @RequestParam(value = "horarios", required = false) List<String> horarios,
                                @RequestParam(value = "preferenciasEstudio", required = false) List<String> preferencias) {
        
        // Obtener usuario actual para mantener ID
        Usuario usuarioActual = usuarioService.obtener();
        if (usuarioActual != null) {
            usuario.setId(usuarioActual.getId());
        }
        
        // Agregar materias, horarios y preferencias si se proporcionaron
        if (materias != null) {
            usuario.setMaterias(materias);
        }
        if (horarios != null) {
            usuario.setHorarios(horarios);
        }
        if (preferencias != null) {
            usuario.setPreferenciasEstudio(preferencias);
        }
        
        usuarioService.guardar(usuario);
        return "redirect:/dashboard";
    }

    @GetMapping("/registrar")
    public String registrar(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        model.addAttribute("active", "perfil");
        model.addAttribute("usuario", usuarioService.obtener());
        return "perfil";
    }

    @PostMapping("/registrar")
    public String registrarPost(@ModelAttribute("usuarioForm") Usuario usuario,
                               @RequestParam(value = "materias", required = false) List<String> materias,
                               @RequestParam(value = "horarios", required = false) List<String> horarios,
                               @RequestParam(value = "preferenciasEstudio", required = false) List<String> preferencias) {
        
        // Agregar materias, horarios y preferencias si se proporcionaron
        if (materias != null) {
            usuario.setMaterias(materias);
        }
        if (horarios != null) {
            usuario.setHorarios(horarios);
        }
        if (preferencias != null) {
            usuario.setPreferenciasEstudio(preferencias);
        }
        
        usuarioService.guardar(usuario);
        return "redirect:/dashboard";
    }

    @GetMapping("/comenzar")
    public String comenzar() {
        return "redirect:/perfil";
    }

    @GetMapping("/sesiones")
    public String sesiones(Model model) {
        model.addAttribute("active", "sesiones");
        model.addAttribute("sesiones", sesionService.listar());
        model.addAttribute("usuario", usuarioService.obtener());
        return "sesiones";
    }

    @PostMapping("/sesiones/crear")
    public String crearSesion(@ModelAttribute Sesion s) {
        sesionService.add(s);
        return "redirect:/sesiones";
    }

    @GetMapping("/graficos")
    public String graficos(Model model) {
        model.addAttribute("active", "graficos");
        model.addAttribute("grupos", grupoService.listar());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("usuario", usuarioService.obtener());
        return "graficos";
    }

    @GetMapping("/logout")
    public String logout() {
        usuarioService.borrar();
        return "redirect:/";
    }
}
