package com.controller;

import com.model.Usuario;
import com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    // Muestra el formulario de registro
    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register"; // Debe coincidir con tu archivo register.html
    }

    // Procesa los datos del formulario
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
        // Validar si el email ya existe
        if (usuarioService.buscarPorEmail(usuario.getEmail()) != null) {
            model.addAttribute("error", "El email ya está registrado.");
            return "register";
        }

        // Guardar el usuario usando nuestro servicio reparado
        usuarioService.registrarUsuario(usuario);

        // Redirigir al login con mensaje de éxito
        return "redirect:/login?registrado";
    }
}