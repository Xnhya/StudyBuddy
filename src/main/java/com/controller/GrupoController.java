package com.controller;

import com.model.GrupoEstudio;
import com.service.GrupoService;
import com.service.UsuarioService;
import com.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MateriaRepository materiaRepository;

    @GetMapping("/buscar")
    public String buscar(Model model) {
        model.addAttribute("active", "buscar");
        model.addAttribute("grupos", grupoService.listarDTO());
        model.addAttribute("usuario", usuarioService.obtener());
        model.addAttribute("materias", materiaRepository.findAll());
        return "buscar";
    }

    @PostMapping("/buscar/crear")
    public String crearGrupo(@RequestParam String nombre,
                             @RequestParam(required = false, name = "materiaId") Long materiaId,
                             @RequestParam(required = false) String horario) {
        GrupoEstudio grupo = new GrupoEstudio();
        grupo.setNombre(nombre);
        grupo.setHorario(horario);
        // Resolver materia por id si viene del formulario
        if (materiaId != null) {
            materiaRepository.findById(materiaId).ifPresent(grupo::setMateria);
        }
        // setear creador si existe
        var u = usuarioService.obtener();
        if (u != null) {
            grupo.setCreador(u);
        }
        grupoService.add(grupo);
        return "redirect:/buscar";
    }

    @PostMapping("/grupos/unirse")
    public String unirseGrupo(@RequestParam("nombre") String nombre) {
        var u = usuarioService.obtener();
        String nombreUsuario = (u != null && u.getNombre() != null) ? u.getNombre() : "Anónimo";
        grupoService.unirse(nombre, nombreUsuario);
        return "redirect:/buscar";
    }

    @GetMapping("/grupos/ver/{nombre}")
    public String verGrupo(@PathVariable String nombre, Model model) {
        GrupoEstudio g = grupoService.buscar(nombre);
        model.addAttribute("grupo", g);
        model.addAttribute("usuario", usuarioService.obtener());
        model.addAttribute("active", "buscar");
        return "grupo-detalles";
    }

    @GetMapping("/grupos/{id}/editar")
    public String editarGrupo(@PathVariable Long id, Model model) {
        GrupoEstudio g = grupoService.buscarPorId(id);
        if (g == null) return "redirect:/buscar";
        // Solo creador puede editar
        var u = usuarioService.obtener();
        if (u == null || g.getCreador() == null || !u.getId().equals(g.getCreador().getId())) {
            return "redirect:/buscar";
        }
        model.addAttribute("grupo", g);
        model.addAttribute("usuario", u);
        return "grupo-form";
    }

    @PostMapping("/grupos/{id}/editar")
    public String actualizarGrupo(@PathVariable Long id, @ModelAttribute GrupoEstudio grupo) {
        GrupoEstudio existente = grupoService.buscarPorId(id);
        if (existente == null) return "redirect:/buscar";
        var u = usuarioService.obtener();
        if (u == null || existente.getCreador() == null || !u.getId().equals(existente.getCreador().getId())) {
            return "redirect:/buscar";
        }
        grupo.setId(id);
        grupo.setCreador(existente.getCreador());
        grupoService.actualizar(grupo);
        return "redirect:/buscar";
    }

    @PostMapping("/grupos/{id}/eliminar")
    public String eliminarGrupo(@PathVariable Long id) {
        GrupoEstudio g = grupoService.buscarPorId(id);
        var u = usuarioService.obtener();
        if (g != null && u != null && g.getCreador() != null && u.getId().equals(g.getCreador().getId())) {
            grupoService.eliminarPorId(id);
        }
        return "redirect:/buscar";
    }
}


