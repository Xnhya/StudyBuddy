package com.service;

import com.model.Usuario;
import com.model.Materia;
import com.repository.UsuarioRepository;
import com.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    private Usuario usuarioActual;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Registrar (recibe JSON con campo "password")
    public Usuario agregar(Usuario usuario) {
        if (usuario == null) return null;
        String raw = usuario.getPassword();
        if (raw == null || raw.trim().isEmpty()) return null;
        String hash = encoder.encode(raw);
        usuario.setPasswordHash(hash);
        // limpiar campo transient para no mantener la raw en memoria (opcional)
        usuario.setPassword(null);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listar() { return usuarioRepository.findAll(); }

    public Usuario buscarPorId(Long id) { return id == null ? null : usuarioRepository.findById(id).orElse(null); }

    public Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;
        return usuarioRepository.findByEmail(email);
    }

    public Usuario buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return null;
        return usuarioRepository.findByNombre(nombre);
    }

    public Usuario actualizar(Usuario usuario) {
        if (usuario != null && usuario.getId() != null && usuarioRepository.existsById(usuario.getId())) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public boolean eliminar(Long id) {
        if (id == null) return false;
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Autenticar con BCrypt
    public Usuario autenticar(String email, String rawPassword) {
        Usuario usuario = buscarPorEmail(email);
        if (usuario != null && rawPassword != null) {
            String storedHash = usuario.getPasswordHash();
            if (storedHash != null && encoder.matches(rawPassword, storedHash)) {
                this.usuarioActual = usuario;
                return usuario;
            }
        }
        return null;
    }

    public Usuario obtener() { return usuarioActual; }

    public void guardar(Usuario usuario) {
        if (usuario != null) {
            if (usuario.getId() == null) agregar(usuario);
            else actualizar(usuario);
            this.usuarioActual = usuario;
        }
    }

    public void asignarMateriasPorNombres(Usuario usuario, List<String> nombresMaterias) {
        if (usuario != null && nombresMaterias != null) {
            List<Materia> materias = new ArrayList<>();
            for (String nombre : nombresMaterias) {
                Materia materia = materiaRepository.findByNombre(nombre);
                if (materia == null) materia = materiaRepository.save(new Materia(nombre));
                materias.add(materia);
            }
            usuario.setMaterias(materias);
        }
    }

    public void borrar() { this.usuarioActual = null; }

    public List<Usuario> buscarPorMateria(String nombreMateria) {
        if (nombreMateria == null || nombreMateria.trim().isEmpty()) return new ArrayList<>();
        Materia materia = materiaRepository.findByNombre(nombreMateria);
        return materia != null ? materia.getUsuarios() : new ArrayList<>();
    }

    public List<Usuario> buscarPorHorario(String horario) {
        if (horario == null || horario.trim().isEmpty()) return new ArrayList<>();
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getHorarios() != null && u.getHorarios().contains(horario))
                .collect(Collectors.toList());
    }

    public List<Usuario> buscarPorPreferencia(String preferencia) {
        if (preferencia == null || preferencia.trim().isEmpty()) return new ArrayList<>();
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getPreferenciasEstudio() != null && u.getPreferenciasEstudio().contains(preferencia))
                .collect(Collectors.toList());
    }
}
