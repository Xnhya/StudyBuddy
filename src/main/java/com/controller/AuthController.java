package com.controller;

import com.model.Usuario;
import com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public String registrar(@RequestBody Usuario usuario) {
        Usuario u = usuarioService.agregar(usuario);
        if (u != null) return "✅ Usuario registrado: " + u.getEmail();
        return "❌ No se pudo registrar (verifica datos)";
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        Usuario u = usuarioService.autenticar(usuario.getEmail(), usuario.getPassword());
        if (u != null) return "✅ Login exitoso: " + u.getEmail();
        return "❌ Credenciales incorrectas";
    }
}
