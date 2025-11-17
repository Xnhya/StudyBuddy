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

    // Método para registrar (este ya lo teníamos)
    public Usuario registrarUsuario(Usuario usuario) {
        String passEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passEncriptada); // Usa el setter de tu modelo

        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER");
        }
        return usuarioRepository.save(usuario);
    }
    
    // Método para buscar por email (este ya lo teníamos)
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email); // Asumiendo que tu Repo tiene findByEmail
    }

    // --- LÓGICA DE TOKEN ADAPTADA DE TUS NUEVOS ARCHIVOS ---

    /**
     * Genera un token de recuperación y lo guarda en el usuario.
     * (Adaptado de UserServicio.java)
     */
    public String generarTokenRecuperacion(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        
        // Usamos los campos de nuestro modelo 'studybuddy'
        usuario.setTokenRecuperacion(token);
        usuario.setTokenExpiracion(LocalDateTime.now().plusMinutes(15)); // 15 minutos de vida
        
        usuarioRepository.save(usuario);
        return token;
    }

    /**
     * Busca un usuario por su token de recuperación.
     * (Adaptado de UserServicio.java)
     */
    public Usuario buscarPorTokenRecuperacion(String token) {
        return usuarioRepository.findByTokenRecuperacion(token);
    }

    /**
     * Cambia la contraseña del usuario.
     * (Adaptado de UserServicio.java)
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