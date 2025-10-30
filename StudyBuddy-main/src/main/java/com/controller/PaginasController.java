package com.controller;

// Importamos las clases y repositorios que SÍ existen
import com.model.GrupoEstudio; // Se llamaba 'Grupo'
import com.model.Sesion;
import com.model.Usuario;
import com.model.Carrera;
import com.model.Facultad; // Necesario para los dropdowns
import com.service.GrupoService;
import com.service.SesionService;
import com.service.UsuarioService;
import com.repository.CarreraRepository;
import com.repository.FacultadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;
import java.util.Optional; // Necesario para findById

@Controller
@SessionAttributes("usuarioLogueado")
public class PaginasController {

    private final GrupoService grupoService;
    private final UsuarioService usuarioService;
    private final SesionService sesionService;
    private final CarreraRepository carreraRepo;
    private final FacultadRepository facultadRepo;

    // --- CAMBIO: Inyección por Constructor (mejor práctica) ---
    @Autowired
    public PaginasController(GrupoService grupoService, UsuarioService usuarioService, SesionService sesionService, CarreraRepository carreraRepo, FacultadRepository facultadRepo) {
        this.grupoService = grupoService;
        this.usuarioService = usuarioService;
        this.sesionService = sesionService;
        this.carreraRepo = carreraRepo;
        this.facultadRepo = facultadRepo;
    }

    @ModelAttribute("usuarioLogueado")
    public Usuario getUsuarioLogueado() {
        return null; // ¡Perfecto!
    }

    // --- SIN CAMBIOS: Esta sección de login/logout está 100% correcta ---

    @GetMapping({"/", "/landing"})
    public String landing(Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        model.addAttribute("active", "landing");
        model.addAttribute("usuario", usuarioLogueado);
        return "landing";
    }

    @GetMapping("/login")
    public String login(Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        if (usuarioLogueado != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Usuario usuario = usuarioService.autenticar(email, password);
        if (usuario != null) {
            model.addAttribute("usuarioLogueado", usuario); // ¡Perfecto!
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete(); // ¡Perfecto!
        return "redirect:/";
    }

    // --- SECCIÓN DE GRUPOS (CON CAMBIOS) ---

    @GetMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        model.addAttribute("active", "buscar");
        model.addAttribute("grupos", grupoService.listar()); // Devuelve List<GrupoEstudio>
        model.addAttribute("usuario", usuarioLogueado);
        return "buscar";
    }

    @PostMapping("/buscar/crear")
    public String crearGrupo(@ModelAttribute GrupoEstudio grupo, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        if (usuarioLogueado == null) return "redirect:/login"; // Proteger
        grupo.setCreador(usuarioLogueado); // Asignar el creador
        grupoService.agregar(grupo);
        return "redirect:/buscar";
    }

    @PostMapping("/grupos/unirse")
    public String unirseGrupo(@RequestParam("idGrupo") Integer idGrupo, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) { // --- CAMBIO: Long -> Integer
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // TODO: Implementar lógica de unirse en GrupoService
        // GrupoEstudio grupo = grupoService.buscarPorId(idGrupo);
        // Usuario usuario = usuarioService.buscarPorId(usuarioLogueado.getId()); // Cuidado si el ID es Long o Integer
        // if (grupo != null && usuario != null) {
        //    grupo.getMiembros().add(usuario);
        //    grupoService.actualizar(grupo);
        // }

        System.out.println("Lógica de 'unirse' pendiente de implementar en el servicio.");

        return "redirect:/buscar";
    }

    // --- CAMBIO AQUÍ: Long -> Integer ---
    @GetMapping("/grupos/ver/{id}")
    public String verGrupo(@PathVariable Integer id, Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {

        GrupoEstudio g = grupoService.buscarPorId(id); // Ahora coincide

        model.addAttribute("grupo", g);
        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("active", "buscar");
        return "grupo-detalle";
    }

    // --- SECCIÓN DE USUARIO (CON CAMBIOS) ---

    @GetMapping("/dashboard")
    public String dashboard(Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        model.addAttribute("active", "dashboard");
        model.addAttribute("grupos", grupoService.listar());
        model.addAttribute("usuario", usuarioLogueado);
        return "dashboard";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        model.addAttribute("active", "perfil");
        // Asegúrate de pasar una copia fresca del usuario para evitar problemas de sesión
        Usuario usuarioParaForm = usuarioService.buscarPorId(usuarioLogueado.getId());
        model.addAttribute("usuarioForm", usuarioParaForm != null ? usuarioParaForm : new Usuario());
        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("facultades", facultadRepo.findAll());
        return "perfil";
    }

    @PostMapping("/perfil")
    public String guardarPerfil(@ModelAttribute("usuarioForm") Usuario usuarioForm,
                                @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado,
                                Model model,
                                @RequestParam(value = "carreraId", required = false) Integer carreraId) {

        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // Buscar el usuario existente para actualizarlo
        Usuario usuarioExistente = usuarioService.buscarPorId(usuarioLogueado.getId());
        if (usuarioExistente == null) {
            // Manejar error si el usuario no se encuentra
            return "redirect:/login";
        }

        // Actualizar solo los campos permitidos del formulario
        usuarioExistente.setNombre(usuarioForm.getNombre());
        usuarioExistente.setApellido(usuarioForm.getApellido());
        // No actualizamos email ni password aquí por seguridad, debería ser un proceso aparte

        if (carreraId != null) {
            Optional<Carrera> carreraOpt = carreraRepo.findById(carreraId);
            carreraOpt.ifPresent(usuarioExistente::setCarrera);
        } else {
             usuarioExistente.setCarrera(null); // Permitir quitar la carrera
        }

        Usuario usuarioActualizado = usuarioService.actualizar(usuarioExistente);
        model.addAttribute("usuarioLogueado", usuarioActualizado); // Actualizar sesión

        return "redirect:/dashboard";
    }

    @GetMapping("/registrar")
    public String registrar(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        model.addAttribute("facultades", facultadRepo.findAll());
        return "perfil"; // Usa la misma vista
    }

    @PostMapping("/registrar")
    public String registrarPost(@ModelAttribute("usuarioForm") Usuario usuario,
                                @RequestParam(value = "carreraId", required = false) Integer carreraId,
                                Model model) {

        if (carreraId != null) {
            Optional<Carrera> carreraOpt = carreraRepo.findById(carreraId);
            carreraOpt.ifPresent(usuario::setCarrera);
        }
        
        // ¡IMPORTANTE! Hashear la contraseña antes de guardar
        // String hashedPassword = passwordEncoder.encode(usuario.getPassword());
        // usuario.setPassword(hashedPassword);
        // Por ahora, guardamos la contraseña como viene (inseguro)

        Usuario usuarioGuardado = usuarioService.agregar(usuario);
        model.addAttribute("usuarioLogueado", usuarioGuardado); // Iniciar sesión

        return "redirect:/dashboard";
    }

    // --- SECCIÓN DE SESIONES Y GRÁFICOS (SIN CAMBIOS POR AHORA) ---

    @GetMapping("/sesiones")
    public String sesiones(Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        model.addAttribute("active", "sesiones");
        model.addAttribute("sesiones", sesionService.listar());
        model.addAttribute("usuario", usuarioLogueado);
        return "sesiones";
    }

    @PostMapping("/sesiones/crear")
    public String crearSesion(@ModelAttribute Sesion s) {
        sesionService.add(s);
        return "redirect:/sesiones";
    }

    @GetMapping("/graficos")
    public String graficos(Model model, @ModelAttribute("usuarioLogueado") Usuario usuarioLogueado) {
        model.addAttribute("active", "graficos");
        model.addAttribute("grupos", grupoService.listar());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("usuario", usuarioLogueado);
        return "graficos";
    }
}