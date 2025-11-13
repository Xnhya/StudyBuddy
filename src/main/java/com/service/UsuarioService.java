package com.service;

import com.model.Usuario;
import com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime; // Importante para la fecha
import java.util.UUID; // Para generar el código aleatorio

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // --- NUEVO MÉTODO PARA RECUPERACIÓN ---
    public String generarTokenRecuperacion(Usuario usuario) {
        // 1. Generar un token único (ej: "550e8400-e29b...")
        String token = UUID.randomUUID().toString();
        
        // 2. Guardarlo en el usuario
        usuario.setTokenRecuperacion(token);
        
        // 3. Establecer expiración (15 minutos desde ahora)
        usuario.setTokenExpiracion(LocalDateTime.now().plusMinutes(15));
        
        // 4. Guardar cambios en la BD
        usuarioRepository.save(usuario);
        
        return token;
    }
    
    // --- NUEVO MÉTODO PARA VALIDAR TOKEN Y CAMBIAR PASSWORD ---
    public Usuario buscarPorToken(String token) {
        // En un caso real, haríamos esto en el repositorio: findByTokenRecuperacion(token)
        // Pero podemos hacerlo simple iterando o agregando el método al Repo.
        // Por ahora, asumamos que agregas esto a UsuarioRepository: Usuario findByTokenRecuperacion(String token);
        return null; // Ojo: Necesitamos actualizar tu repositorio para esto.
    }
    
    public void actualizarPassword(Usuario usuario, String nuevaPassword) {
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuario.setTokenRecuperacion(null); // Borramos el token usado
        usuario.setTokenExpiracion(null);
        usuarioRepository.save(usuario);
    }
}