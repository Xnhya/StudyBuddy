package com.controller;

import com.model.Usuario;
import com.repository.FacultadRepository;
import com.repository.CarreraRepository;
import com.service.GrupoService;
import com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PaginasController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private CarreraRepository carreraRepository;

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
        model.addAttribute("facultades", facultadRepository.findAll());
        return "perfil";
    }

    @PostMapping("/perfil")
    public String guardarPerfil(@ModelAttribute("usuarioForm") Usuario usuario,
                                @RequestParam(value = "materias", required = false) List<String> materias,
                                @RequestParam(value = "horarios", required = false) List<String> horarios,
                                @RequestParam(value = "preferenciasEstudio", required = false) List<String> preferencias,
                                @RequestParam(value = "idCarrera", required = false) Long idCarrera) {
        
        // Obtener usuario actual para mantener ID
        Usuario usuarioActual = usuarioService.obtener();
        if (usuarioActual != null) {
            usuario.setId(usuarioActual.getId());
        }
        
        // Asignar carrera si se envió
        if (idCarrera != null) {
            carreraRepository.findById(idCarrera).ifPresent(usuario::setCarrera);
        }
        // Agregar materias, horarios y preferencias si se proporcionaron
        if (materias != null) {
            usuarioService.asignarMateriasPorNombres(usuario, materias);
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
        model.addAttribute("facultades", facultadRepository.findAll());
        return "register";
    }

    @PostMapping("/registrar")
    public String registrarPost(@ModelAttribute("usuarioForm") Usuario usuario,
                               @RequestParam(value = "materias", required = false) List<String> materias,
                               @RequestParam(value = "horarios", required = false) List<String> horarios,
                               @RequestParam(value = "preferenciasEstudio", required = false) List<String> preferencias,
                               @RequestParam(value = "idCarrera", required = false) Long idCarrera) {
        
        // Asignar carrera si se envió
        if (idCarrera != null) {
            carreraRepository.findById(idCarrera).ifPresent(usuario::setCarrera);
        }
        // Agregar materias, horarios y preferencias si se proporcionaron
        if (materias != null) {
            usuarioService.asignarMateriasPorNombres(usuario, materias);
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
