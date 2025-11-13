package com.controller;

import com.model.Usuario;
import com.service.EmailService;
import com.service.UsuarioService;
import com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;

@Controller
public class RecuperacionController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private EmailService emailService;
    @Autowired private UsuarioRepository usuarioRepository; // Para buscar por token directo

    // 1. Mostrar formulario de "Olvidé mi contraseña"
    @GetMapping("/recover")
    public String mostrarFormularioRecover() {
        return "recuperar-password"; // Asegúrate de tener este HTML
    }

    // 2. Procesar el correo enviado
    @PostMapping("/recover")
    public String procesarRecover(@RequestParam("email") String email, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        
        if (usuario != null) {
            // Generar token en UsuarioService (ya lo hicimos en el paso anterior)
            String token = usuarioService.generarTokenRecuperacion(usuario);
            
            // Crear el link (Ajusta el puerto si no es 8083)
            String link = "http://localhost:8083/reset?token=" + token;
            
            emailService.enviarCorreo(email, "Recuperación de Clave - StudyBuddy", 
                    "Hola " + usuario.getNombre() + ",\n\n" +
                    "Para restablecer tu contraseña, haz clic aquí:\n" + link);
        }
        
        // Por seguridad, siempre decimos "si existe, se envió"
        model.addAttribute("mensaje", "Si el correo existe, recibirás un enlace.");
        return "login"; 
    }

    // 3. Validar el token cuando el usuario hace clic en el correo
    @GetMapping("/reset")
    public String mostrarFormularioReset(@RequestParam("token") String token, Model model) {
        // NOTA: Necesitas agregar 'findByTokenRecuperacion' en tu UsuarioRepository
        Usuario usuario = usuarioRepository.findByTokenRecuperacion(token);

        if (usuario == null || usuario.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "El enlace es inválido o ha expirado.");
            return "login";
        }

        model.addAttribute("token", token);
        return "restablecer-password"; // HTML para poner la nueva clave
    }

    // 4. Guardar la nueva contraseña
    @PostMapping("/reset")
    public String guardarNuevaClave(@RequestParam("token") String token, 
                                    @RequestParam("password") String password) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacion(token);
        
        if (usuario != null) {
            usuarioService.actualizarPassword(usuario, password);
        }
        
        return "redirect:/login?restablecido";
    }
}