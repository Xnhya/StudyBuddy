package com.controller;

import com.model.Sesion;
import com.service.SesionService;
import com.service.UsuarioService;
import com.dto.SesionListadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sesiones")
public class SesionController {

    @Autowired
    private SesionService sesionService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public String sesiones(Model model) {
        List<SesionListadoDTO> sesiones = sesionService.listarDTO();
        model.addAttribute("active", "sesiones");
        model.addAttribute("sesiones", sesiones);
        model.addAttribute("usuario", usuarioService.obtener());
        return "sesiones";
    }

    @PostMapping("/crear")
    public String crearSesion(@ModelAttribute Sesion s) {
        var u = usuarioService.obtener();
        if (u != null) {
            s.setUsuario(u);
        }
        sesionService.add(s);
        return "redirect:/sesiones";
    }

    @GetMapping("/{id}/editar")
    public String editarSesion(@PathVariable Long id, Model model) {
        Sesion s = sesionService.buscarPorId(id);
        if (s == null) return "redirect:/sesiones";
        var u = usuarioService.obtener();
        if (u == null || s.getUsuario() == null || !u.getId().equals(s.getUsuario().getId())) {
            return "redirect:/sesiones";
        }
        model.addAttribute("sesion", s);
        model.addAttribute("usuario", u);
        return "sesion-form";
    }

    @PostMapping("/{id}/editar")
    public String actualizarSesion(@PathVariable Long id, @ModelAttribute Sesion sesion) {
        Sesion existente = sesionService.buscarPorId(id);
        if (existente == null) return "redirect:/sesiones";
        var u = usuarioService.obtener();
        if (u == null || existente.getUsuario() == null || !u.getId().equals(existente.getUsuario().getId())) {
            return "redirect:/sesiones";
        }
        sesion.setId(id);
        sesion.setUsuario(existente.getUsuario());
        sesionService.actualizar(sesion);
        return "redirect:/sesiones";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarSesion(@PathVariable Long id) {
        Sesion s = sesionService.buscarPorId(id);
        var u = usuarioService.obtener();
        if (s != null && u != null && s.getUsuario() != null && u.getId().equals(s.getUsuario().getId())) {
            sesionService.eliminar(id);
        }
        return "redirect:/sesiones";
    }
}


