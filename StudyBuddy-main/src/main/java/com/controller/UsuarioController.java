package com.controller;

import com.model.Usuario;
import com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private Usuario getUsuarioActual(Principal principal) {
        if (principal == null) return null;
        return usuarioService.buscarPorEmail(principal.getName());
    }

    @GetMapping
    public String listar(Model model, Principal principal) {
        Usuario u = getUsuarioActual(principal);
        if (u == null) return "redirect:/login";
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("usuario", u);
        model.addAttribute("active", "dashboard");
        return "usuarios";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable Integer id, Model model, Principal principal) {
        Usuario u = getUsuarioActual(principal);
        if (u == null) return "redirect:/login";
        Usuario objetivo = usuarioService.buscarPorId(id);
        if (objetivo == null) return "redirect:/usuarios";
        model.addAttribute("u", objetivo);
        model.addAttribute("usuario", u);
        return "usuario-detalle";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id, Principal principal) {
        if (getUsuarioActual(principal) == null) return "redirect:/login";
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }
}

