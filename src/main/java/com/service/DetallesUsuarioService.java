package com.service;

import com.model.Usuario;
import com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DetallesUsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Buscar el usuario en TU base de datos por email
        Usuario usuario = usuarioRepository.findByEmail(email);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        // 2. Configurar el rol (Spring Security espera "ROLE_ADMIN" o "ROLE_USER")
        // Si tu base de datos guarda "ADMIN", aquí le agregamos "ROLE_"
        String rol = usuario.getRol(); 
        if (rol == null) {
            rol = "USER"; // Rol por defecto si viene nulo
        }
        List<GrantedAuthority> autoridades = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + rol)
        );

        // 3. Retornar el objeto User de Spring Security con TUS datos
        return new User(
                usuario.getEmail(),       // El "username" para el login
                usuario.getPassword(),    // La contraseña encriptada de la BD
                autoridades               // Los permisos/roles
        );
    }
}