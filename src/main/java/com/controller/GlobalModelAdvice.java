package com.controller;

import com.model.Usuario;
import com.repository.UsuarioRepository;
import com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute("usuario")
    public Usuario addUsuarioToModel() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            // Ignorar usuario anónimo por defecto de Spring Security
            if (email != null && !"anonymousUser".equals(email)) {
                Usuario u = usuarioRepository.findByEmail(email);
                if (u == null) {
                    // Crear usuario en BD basado en principal de Spring Security
                    u = new Usuario();
                    u.setEmail(email);
                    u.setNombre(email);
                    u.setApellido("");
                    u.setPassword("N/A");
                    u = usuarioService.agregar(u);
                }
                // Sincronizar con servicio para lógica existente
                usuarioService.guardar(u);
                return u;
            }
        }
        return null;
    }
}


