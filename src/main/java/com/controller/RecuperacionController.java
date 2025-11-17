package com.controller;

import com.model.Usuario;
import com.service.EmailService;
import com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class RecuperacionController {

    @Autowired
    private UsuarioService usuarioService; // El servicio de StudyBuddy

    @Autowired
    private EmailService emailService; // El servicio de StudyBuddy

    /**
     * Muestra el formulario para pedir el email.
     * (Lógica de ControladorRegistro.java)
     */
    @GetMapping("/recover")
    public String mostrarFormularioRecover() {
        return "recuperar-password"; // Vistal HTML de StudyBuddy
    }

    /**
     * Procesa la solicitud de recuperación.
     * (Lógica de ControladorRegistro.java)
     */
    @PostMapping("/recover")
    public String procesarRecover(@RequestParam("email") String email, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        
        if (usuario != null) {
            String token = usuarioService.generarTokenRecuperacion(usuario);
            String link = "http://localhost:8083/reset?token=" + token; // Puerto de StudyBuddy
            
            emailService.enviarCorreo(
                email, 
                "Recuperación de Contraseña - StudyBuddy", 
                "Hola " + usuario.getNombre() + ",\n\nPara restablecer tu contraseña, haz clic en el enlace:\n" + link
            );
        }
        
        model.addAttribute("mensaje", "Si el correo está registrado, recibirás un enlace.");
        return "login"; // Vista HTML de StudyBuddy
    }

    /**
     * Muestra la página para poner la nueva clave.
     * (Lógica de ControladorRegistro.java)
     */
    @GetMapping("/reset")
    public String mostrarFormularioReset(@RequestParam("token") String token, Model model) {
        Usuario usuario = usuarioService.buscarPorTokenRecuperacion(token);
        
        // Validamos el token Y la expiración (gracias a nuestro modelo de StudyBuddy)
        if (usuario == null || usuario.getTokenExpiracion() == null || usuario.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "El token es inválido o ha expirado.");
            return "login"; // Vista HTML de StudyBuddy
        }
        
        model.addAttribute("token", token);
        return "restablecer-password"; // Vista HTML de StudyBuddy
    }

    /**
     * Procesa y guarda la nueva clave.
     * (Lógica de ControladorRegistro.java)
     */
    @PostMapping("/reset")
    public String guardarNuevaClave(@RequestParam("token") String token, 
                                    @RequestParam("password") String password) {
        
        Usuario usuario = usuarioService.buscarPorTokenRecuperacion(token);
        
        if (usuario != null && usuario.getTokenExpiracion() != null && usuario.getTokenExpiracion().isAfter(LocalDateTime.now())) {
            usuarioService.actualizarPassword(usuario, password);
        }
        
        return "redirect:/login?restablecido";
    }
}