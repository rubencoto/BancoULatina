package cr.ac.ulatina.banco.controlador;

import cr.ac.ulatina.banco.entidad.Usuario;
import cr.ac.ulatina.banco.repositorio.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    private final UsuarioRepositorio usuarioRepositorio;
    private final CuentaRepositorio cuentaRepositorio;
    private final TarjetaDebitoRepositorio tarjetaDebitoRepositorio;

    public ClienteControlador(UsuarioRepositorio usuarioRepositorio, CuentaRepositorio cuentaRepositorio,
                              TarjetaDebitoRepositorio tarjetaDebitoRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.cuentaRepositorio = cuentaRepositorio;
        this.tarjetaDebitoRepositorio = tarjetaDebitoRepositorio;
    }

    @GetMapping("/inicio")
    public String inicio() { return "cliente/inicio"; }

    @GetMapping("/billetera")
    public String billetera(Authentication auth, Model model) {
        Usuario yo = usuarioRepositorio.findByCorreo(auth.getName()).orElseThrow();
        model.addAttribute("cuentas", cuentaRepositorio.findByDueno(yo));
        model.addAttribute("tarjetas", tarjetaDebitoRepositorio.findByDueno(yo));
        return "cliente/billetera";
    }
}
