package com.service;

import com.model.Usuario;
import com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // Importante
import java.util.UUID; // Importante

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // (Este método de registro ya lo tenías y está perfecto)
    public Usuario registrarUsuario(Usuario usuario) {
        String passEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passEncriptada);
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER");
        }
        return usuarioRepository.save(usuario);
    }
    
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // --- LÓGICA DE TOKEN ADAPTADA DE TUS EJEMPLOS ---

    /**
     * Genera un token y lo guarda en el usuario.
     * (Lógica de UserServicio.java)
     */
    public String generarTokenRecuperacion(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        
        // Usamos los campos de TU modelo Usuario (studybuddy)
        usuario.setTokenRecuperacion(token);
        usuario.setTokenExpiracion(LocalDateTime.now().plusMinutes(15)); // 15 min de vida
        
        usuarioRepository.save(usuario);
        return token;
    }

    /**
     * Busca un usuario por su token.
     * (Lógica de UserServicio.java)
     */
    public Usuario buscarPorTokenRecuperacion(String token) {
        return usuarioRepository.findByTokenRecuperacion(token);
    }

    /**
     * Cambia la contraseña del usuario y limpia el token.
     * (Lógica de UserServicio.java)
     */
    public void actualizarPassword(Usuario usuario, String nuevaPassword) {
        // Encriptamos la nueva clave
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        
        // Limpiamos el token para que no se reutilice
        usuario.setTokenRecuperacion(null);
        usuario.setTokenExpiracion(null);
        
        usuarioRepository.save(usuario);
    }
}