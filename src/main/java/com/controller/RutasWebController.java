package com.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para las páginas web estáticas y principales
 * (Login, Landing, Dashboard).
 */
@Controller
public class RutasWebController {

    /**
     * Mapeo para la página raíz (landing page).
     * Permitido en SecurityConfig.
     */
    @GetMapping("/")
    public String landingPage() {
        return "landing"; // Busca /templates/landing.html
    }

    /**
     * Mapeo alternativo para la landing page.
     * Permitido en SecurityConfig.
     */
    @GetMapping("/landing")
    public String landingPageAlt() {
        return "landing"; // Busca /templates/landing.html
    }

    /**
     * Mapeo para la página de login.
     * Permitido en SecurityConfig.
     * Spring Security redirige aquí.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Busca /templates/login.html
    }

    /**
     * Mapeo para el dashboard principal.
     * Esta ruta SÍ está protegida por SecurityConfig.
     * Spring Security te enviará aquí después de un login exitoso.
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        // Aquí puedes agregar lógica para cargar datos del dashboard
        return "dashboard"; // Busca /templates/dashboard.html
    }
}