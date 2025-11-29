package com.service;

import com.model.GrupoEstudio;
import com.model.Usuario;
import com.dto.GrupoListadoDTO;
import com.repository.GrupoRepository;
import com.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ✅ corregido: nombre del método correcto
    public void add(GrupoEstudio g) {
        if (g != null && buscar(g.getNombre()) == null) {
            grupoRepository.save(g);
        }
    }

    public List<GrupoEstudio> listar() {
        return grupoRepository.findAll();
    }

    // ✅ corregido: invocación correcta del DTO
    public List<GrupoListadoDTO> listarDTO() {
        return grupoRepository.findAllGruposListado();
    }

    public GrupoEstudio buscar(String nombre) {
        if (nombre == null) return null;
        return grupoRepository.findByNombre(nombre); // ✅ ese método SÍ existe
    }

    public GrupoEstudio buscarPorId(Long id) {
        if (id == null) return null;
        return grupoRepository.findById(id).orElse(null);
    }

    public GrupoEstudio actualizar(GrupoEstudio grupo) {
        if (grupo != null && grupo.getId() != null) {
            if (grupoRepository.existsById(grupo.getId())) {
                return grupoRepository.save(grupo);
            }
        }
        return null;
    }

    // ✅ corregido: método compatible con tu entity y repo
    public void unirse(String nombreGrupo, String nombreUsuario) {
        GrupoEstudio grupo = buscar(nombreGrupo);
        Usuario usuario = usuarioRepository.findByNombre(nombreUsuario);

        if (grupo != null && usuario != null) {
            grupo.addMiembro(usuario);
            grupoRepository.save(grupo);
        }
    }

    public void unirsePorId(Long idGrupo, Long idUsuario) {
        GrupoEstudio grupo = grupoRepository.findById(idGrupo).orElse(null);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

        if (grupo != null && usuario != null) {
            grupo.addMiembro(usuario);
            grupoRepository.save(grupo);
        }
    }

    public void eliminar(String nombre) {
        GrupoEstudio grupo = buscar(nombre);
        if (grupo != null) {
            grupoRepository.delete(grupo);
        }
    }

    public void eliminarPorId(Long id) {
        if (id != null) {
            grupoRepository.deleteById(id);
        }
    }
}
