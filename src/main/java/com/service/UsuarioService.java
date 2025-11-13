package com.service;

import com.model.Usuario;
import com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para registrar un nuevo usuario
    public Usuario registrarUsuario(Usuario usuario) {
        // 1. Encriptamos la contraseña antes de guardar
        String passEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passEncriptada);

        // 2. Asignamos el rol por defecto si no trae uno
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER");
        }

        // 3. Guardamos en base de datos
        return usuarioRepository.save(usuario);
    }
    
    // Método auxiliar para buscar por email (usado en validaciones)
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}