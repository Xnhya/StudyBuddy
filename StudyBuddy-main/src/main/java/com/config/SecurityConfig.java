package com.config;

import com.service.UsuarioService; // <-- ¡NUEVA IMPORTACIÓN!
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService; // <-- ¡NUEVA IMPORTACIÓN!
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Inyectaremos el UsuarioService directamente aquí si fuera necesario, 
    // pero la forma más limpia es proveer el bean de UserDetailsService.

    /**
     * Define el bean UserDetailsService para que Spring Security use
     * tu UsuarioService en lugar del usuario auto-generado.
     */
    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        // Simplemente devuelve tu servicio que ya implementa UserDetailsService
        return usuarioService; 
    }
    
    /**
     * Configuración del filtro de seguridad HTTP
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/landing", "/registrar", "/login", "/api/**", "/css/**", "/js/**", "/static/**").permitAll()
                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .usernameParameter("email") 
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") 
                .logoutSuccessUrl("/") 
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); 

        return http.build();
    }

    /**
     * Define el encriptador de contraseñas (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}