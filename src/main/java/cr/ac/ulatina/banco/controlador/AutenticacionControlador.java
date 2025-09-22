package cr.ac.ulatina.banco.controlador;

import cr.ac.ulatina.banco.entidad.Rol;
import cr.ac.ulatina.banco.entidad.Usuario;
import cr.ac.ulatina.banco.repositorio.UsuarioRepositorio;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AutenticacionControlador {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public AutenticacionControlador(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/iniciar-sesion")
    public String mostrarLogin(@RequestParam(required = false) String tipo, Model model) {
        model.addAttribute("tipo", tipo);
        return "auth/iniciar-sesion";
    }

    @GetMapping("/cliente/registrar")
    public String mostrarRegistroCliente(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("tipoUsuario", "cliente");
        return "auth/cliente/registrar";
    }

    @GetMapping("/trabajador/registrar")
    public String mostrarRegistroTrabajador(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("tipoUsuario", "trabajador");
        return "auth/trabajador/registrar";
    }

    @PostMapping("/cliente/registrar")
    public String registrarCliente(@ModelAttribute Usuario usuario,
                                   @RequestParam String contrasena,
                                   RedirectAttributes redirectAttributes) {
        try {
            // Verificar si el correo ya existe
            if (usuarioRepositorio.findByCorreo(usuario.getCorreo()).isPresent()) {
                redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está registrado");
                return "redirect:/auth/cliente/registrar";
            }

            usuario.setHashContrasena(passwordEncoder.encode(contrasena));
            usuario.setRol(Rol.CLIENTE);
            usuario.setHabilitado(true);
            usuarioRepositorio.save(usuario);

            redirectAttributes.addFlashAttribute("success", "Registro exitoso. Ahora puede iniciar sesión.");
            return "redirect:/auth/iniciar-sesion?tipo=cliente";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "redirect:/auth/cliente/registrar";
        }
    }

    @PostMapping("/trabajador/registrar")
    public String registrarTrabajador(@ModelAttribute Usuario usuario,
                                      @RequestParam String contrasena,
                                      RedirectAttributes redirectAttributes) {
        try {
            // Verificar si el correo ya existe
            if (usuarioRepositorio.findByCorreo(usuario.getCorreo()).isPresent()) {
                redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está registrado");
                return "redirect:/auth/trabajador/registrar";
            }

            usuario.setHashContrasena(passwordEncoder.encode(contrasena));
            usuario.setRol(Rol.TRABAJADOR);
            usuario.setHabilitado(true);
            usuarioRepositorio.save(usuario);

            redirectAttributes.addFlashAttribute("success", "Registro exitoso. Ahora puede iniciar sesión.");
            return "redirect:/auth/iniciar-sesion?tipo=trabajador";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "redirect:/auth/trabajador/registrar";
        }
    }
}
