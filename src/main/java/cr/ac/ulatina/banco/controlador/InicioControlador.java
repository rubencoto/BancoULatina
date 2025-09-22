package cr.ac.ulatina.banco.controlador;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioControlador {

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/despues-login")
    public String despuesLogin(Authentication auth) {
        if (auth == null) return "redirect:/";
        boolean esTrabajador = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TRABAJADOR"));
        return esTrabajador ? "redirect:/trabajador/inicio" : "redirect:/cliente/inicio";
    }
}
