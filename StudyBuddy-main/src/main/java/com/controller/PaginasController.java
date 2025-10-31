package com.controller;

import java.security.Principal; // <-- ¡CAMBIO! Importar Principal
import java.util.Optional;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder; // <-- ¡CAMBIO! Importar
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// --- ¡CAMBIO! Ya no usamos SessionAttributes ni SessionStatus ---

import com.model.Carrera;
import com.model.GrupoEstudio;
import com.model.Sesion;
import com.model.Usuario;
import com.repository.CarreraRepository;
import com.repository.FacultadRepository;
import com.service.GrupoService;
import com.service.SesionService;
import com.service.UsuarioService;

@Controller
// --- ¡CAMBIO! Se elimina @SessionAttributes("usuarioLogueado") ---
public class PaginasController {

    private final GrupoService grupoService;
    private final UsuarioService usuarioService;
    private final SesionService sesionService;
    private final CarreraRepository carreraRepo;
    private final FacultadRepository facultadRepo;
    private final PasswordEncoder passwordEncoder; // <-- ¡CAMBIO!

    // --- ¡CAMBIO! Inyectamos PasswordEncoder ---
    public PaginasController(GrupoService grupoService, UsuarioService usuarioService, SesionService sesionService,
            CarreraRepository carreraRepo, FacultadRepository facultadRepo, PasswordEncoder passwordEncoder) {
        this.grupoService = grupoService;
        this.usuarioService = usuarioService;
        this.sesionService = sesionService;
        this.carreraRepo = carreraRepo;
        this.facultadRepo = facultadRepo;
        this.passwordEncoder = passwordEncoder; // <-- ¡CAMBIO!
    }

    /**
     * ¡CAMBIO! Método de ayuda para obtener el
     * usuario de nuestra BDD a partir del 'Principal' de Spring Security.
     */
    private Usuario getUsuarioActual(Principal principal) {
        if (principal == null) {
            return null;
        }
        // principal.getName() nos da el 'username', que configuramos que sea el email
        return usuarioService.buscarPorEmail(principal.getName());
    }

    // --- ¡CAMBIO! Se elimina @ModelAttribute("usuarioLogueado") ---

    // --- LOGIN / LOGOUT (CORREGIDOS) ---

    @GetMapping({ "/", "/landing" })
    public String landing(Model model, Principal principal) { // <-- ¡CAMBIO!
        model.addAttribute("active", "landing");
        model.addAttribute("usuario", getUsuarioActual(principal)); // <-- ¡CAMBIO!
        return "landing";
    }

    @GetMapping("/login")
    public String login(Model model, Principal principal) { // <-- ¡CAMBIO!
        if (principal != null) {
            // Si ya está logueado, redirige al dashboard
            return "redirect:/dashboard";
        }
        return "login";
    }

    // --- ¡MÉTODO 'procesarLogin' ELIMINADO! ---
    // Spring Security se encarga (intercepta el POST a /login).

    // --- ¡MÉTODO 'logout' ELIMINADO! ---
    // Spring Security se encarga (intercepta el GET/POST a /logout).

    // --- SECCIÓN DE GRUPOS (CON CAMBIOS) ---

    @GetMapping("/buscar")
    public String buscar(Model model, Principal principal) { // <-- ¡CAMBIO!
        Usuario usuarioLogueado = getUsuarioActual(principal);
        // Spring Security ya protege esta ruta, pero es buena práctica verificar
        if (usuarioLogueado == null)
            return "redirect:/login";

        model.addAttribute("active", "buscar");
        model.addAttribute("grupos", grupoService.listarDTO());
        model.addAttribute("usuario", usuarioLogueado);
        return "buscar";
    }

    @PostMapping("/buscar/crear")
    public String crearGrupo(@ModelAttribute GrupoEstudio grupo, Principal principal) { // <-- ¡CAMBIO!
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null)
            return "redirect:/login";

        grupo.setCreador(usuarioLogueado);
        grupoService.agregar(grupo);
        return "redirect:/buscar";
    }

    @PostMapping("/grupos/unirse")
    public String unirseGrupo(@RequestParam("idGrupo") Integer idGrupo, Principal principal) { // <-- ¡CAMBIO!
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        // ... (lógica para unirse) ...
        return "redirect:/buscar";
    }

    @GetMapping("/grupos/ver/{id}")
    public String verGrupo(@PathVariable Integer id, Model model, Principal principal) { // <-- ¡CAMBIO!
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null)
            return "redirect:/login";

        GrupoEstudio g = grupoService.buscarPorId(id);
        model.addAttribute("grupo", g);
        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("active", "buscar");
        // Tu HTML se llama 'grupo-detalles.html', no 'grupo-detalle'
        return "grupo-detalles"; // <-- Corregido
    }

    // --- EDITAR / ELIMINAR GRUPO ---
    @GetMapping("/grupos/{id}/editar")
    public String editarGrupo(@PathVariable Integer id, Model model, Principal principal) {
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null) return "redirect:/login";
        GrupoEstudio grupo = grupoService.buscarPorId(id);
        if (grupo == null) return "redirect:/buscar";
        model.addAttribute("grupo", grupo);
        model.addAttribute("usuario", usuarioLogueado);
        return "grupo-form";
    }

    @PostMapping("/grupos/{id}/editar")
    public String actualizarGrupo(@PathVariable Integer id, @ModelAttribute GrupoEstudio grupo, Principal principal) {
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null) return "redirect:/login";
        GrupoEstudio existente = grupoService.buscarPorId(id);
        if (existente == null) return "redirect:/buscar";
        existente.setNombre(grupo.getNombre());
        existente.setDescripcion(grupo.getDescripcion());
        grupoService.actualizar(existente);
        return "redirect:/grupos/ver/" + id;
    }

    @PostMapping("/grupos/{id}/eliminar")
    public String eliminarGrupo(@PathVariable Integer id, Principal principal) {
        if (getUsuarioActual(principal) == null) return "redirect:/login";
        grupoService.eliminar(id);
        return "redirect:/buscar";
    }

    // --- SECCIÓN DE USUARIO (CON CAMBIOS) ---

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) { // <-- ¡CAMBIO!
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        model.addAttribute("active", "dashboard");
        model.addAttribute("grupos", grupoService.listar());
        model.addAttribute("usuario", usuarioLogueado);
        return "dashboard";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Principal principal) { // <-- ¡CAMBIO!
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        model.addAttribute("active", "perfil");
        Usuario usuarioParaForm = usuarioService.buscarPorId(usuarioLogueado.getId());
        model.addAttribute("usuarioForm", usuarioParaForm != null ? usuarioParaForm : new Usuario());
        model.addAttribute("usuario", usuarioLogueado);
        model.addAttribute("facultades", facultadRepo.findAll());
        return "perfil";
    }

    @PostMapping("/perfil")
    public String guardarPerfil(@ModelAttribute("usuarioForm") Usuario usuarioForm,
            Principal principal, // <-- ¡CAMBIO!
            @RequestParam(value = "carreraId", required = false) Integer carreraId) {

        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        Usuario usuarioExistente = usuarioService.buscarPorId(usuarioLogueado.getId());
        if (usuarioExistente == null) {
            return "redirect:/login";
        }

        usuarioExistente.setNombre(usuarioForm.getNombre());
        usuarioExistente.setApellido(usuarioForm.getApellido());
        usuarioExistente.setEmail(usuarioForm.getEmail()); // Asegúrate de que esto sea correcto (¿se puede cambiar
                                                           // email?)

        // --- ¡CAMBIO IMPORTANTE! Encriptar password SÓLO si se cambió ---
        if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().trim().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuarioForm.getPassword()));
        }

        if (carreraId != null) {
            Optional<Carrera> carreraOpt = carreraRepo.findById(carreraId);
            carreraOpt.ifPresent(usuarioExistente::setCarrera);
        } else {
            usuarioExistente.setCarrera(null);
        }

        usuarioService.actualizar(usuarioExistente);
        return "redirect:/dashboard";
    }

    @GetMapping("/registrar")
    public String registrar(Model model, Principal principal) { // <-- ¡CAMBIO!
        if (principal != null) {
            // Si ya está logueado, no tiene sentido registrarse
            return "redirect:/dashboard";
        }
        model.addAttribute("usuarioForm", new Usuario());
        model.addAttribute("facultades", facultadRepo.findAll());
        // El controller original apuntaba a 'perfil', pero el archivo es
        // 'register.html'
        return "register"; // <-- Corregido
    }

    @PostMapping("/registrar")
    public String registrarPost(@ModelAttribute("usuarioForm") Usuario usuario,
            @RequestParam(value = "carreraId", required = false) Integer carreraId,
            @RequestParam(value = "preferencias", required = false) List<String> preferencias, // <-- AÑADIDO
            Model model) {

        if (carreraId != null) {
            Optional<Carrera> carreraOpt = carreraRepo.findById(carreraId);
            carreraOpt.ifPresent(usuario::setCarrera);
        }

        // --- LÓGICA DE PREFERENCIAS (AÑADIDO) ---
        // Convierte la lista de preferencias (ej. ["MUSICA", "SILENCIO"])
        // en un solo String (ej. "MUSICA,SILENCIO") para la BDD.
        if (preferencias != null && !preferencias.isEmpty()) {
            String preferenciasString = String.join(",", preferencias);
            usuario.setPreferencias(preferenciasString);
        }
        // --- FIN LÓGICA PREFERENCIAS ---

        // Hashear la contraseña (código existente)
        String hashedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(hashedPassword);

        usuarioService.agregar(usuario);

        return "redirect:/login";
    }

    // --- SECCIÓN DE SESIONES Y GRÁFICOS (CON CAMBIOS) ---

    @GetMapping("/sesiones")
    public String sesiones(Model model, Principal principal) { // <-- ¡CAMBIO!
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null)
            return "redirect:/login";

        model.addAttribute("active", "sesiones");
        model.addAttribute("sesiones", sesionService.listar());
        model.addAttribute("usuario", usuarioLogueado);
        return "sesiones";
    }

    @PostMapping("/sesiones/crear")
    public String crearSesion(@ModelAttribute Sesion s, Principal principal) { // <-- ¡CAMBIO!
        if (principal == null)
            return "redirect:/login"; // Proteger
        sesionService.add(s);
        return "redirect:/sesiones";
    }

    // --- EDITAR / ELIMINAR SESIÓN ---
    @GetMapping("/sesiones/{id}/editar")
    public String editarSesion(@PathVariable Integer id, Model model, Principal principal) {
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null) return "redirect:/login";
        Sesion sesion = sesionService.buscarPorId(id);
        if (sesion == null) return "redirect:/sesiones";
        model.addAttribute("sesion", sesion);
        model.addAttribute("usuario", usuarioLogueado);
        return "sesion-form";
    }

    @PostMapping("/sesiones/{id}/editar")
    public String actualizarSesion(@PathVariable Integer id, @ModelAttribute Sesion s, Principal principal) {
        if (getUsuarioActual(principal) == null) return "redirect:/login";
        Sesion existente = sesionService.buscarPorId(id);
        if (existente == null) return "redirect:/sesiones";
        existente.setTema(s.getTema());
        existente.setFecha(s.getFecha());
        existente.setHora(s.getHora());
        existente.setDescripcion(s.getDescripcion());
        sesionService.actualizar(existente);
        return "redirect:/sesiones";
    }

    @PostMapping("/sesiones/{id}/eliminar")
    public String eliminarSesion(@PathVariable Integer id, Principal principal) {
        if (getUsuarioActual(principal) == null) return "redirect:/login";
        sesionService.eliminar(id);
        return "redirect:/sesiones";
    }

    @GetMapping("/graficos")
    public String graficos(Model model, Principal principal) { // <-- ¡CAMBIO!
        Usuario usuarioLogueado = getUsuarioActual(principal);
        if (usuarioLogueado == null)
            return "redirect:/login";

        model.addAttribute("active", "graficos");
        model.addAttribute("grupos", grupoService.listar());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("usuario", usuarioLogueado);
        return "graficos";
    }
}