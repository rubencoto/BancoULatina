package cr.ac.ulatina.banco.controlador;

import cr.ac.ulatina.banco.entidad.Rol;
import cr.ac.ulatina.banco.entidad.Usuario;
import cr.ac.ulatina.banco.repositorio.*;
import cr.ac.ulatina.banco.servicio.TicketServicio;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trabajador")
public class TrabajadorControlador {
    private final UsuarioRepositorio usuarioRepositorio;
    private final TicketServicio ticketServicio;
    private final CuentaRepositorio cuentaRepositorio;
    private final TarjetaDebitoRepositorio tarjetaDebitoRepositorio;
    private final SolicitudProductoRepositorio solicitudProductoRepositorio;
    private final TicketRepositorio ticketRepositorio;
    private final PrestamoRepositorio prestamoRepositorio;

    public TrabajadorControlador(UsuarioRepositorio usuarioRepositorio,
                                 TicketServicio ticketServicio,
                                 CuentaRepositorio cuentaRepositorio,
                                 TarjetaDebitoRepositorio tarjetaDebitoRepositorio,
                                 SolicitudProductoRepositorio solicitudProductoRepositorio,
                                 TicketRepositorio ticketRepositorio,
                                 PrestamoRepositorio prestamoRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.ticketServicio = ticketServicio;
        this.cuentaRepositorio = cuentaRepositorio;
        this.tarjetaDebitoRepositorio = tarjetaDebitoRepositorio;
        this.solicitudProductoRepositorio = solicitudProductoRepositorio;
        this.ticketRepositorio = ticketRepositorio;
        this.prestamoRepositorio = prestamoRepositorio;
    }

    @GetMapping("/inicio")
    public String inicio() { return "trabajador/inicio"; }

    @GetMapping("/clientes")
    public String buscarClientes(@RequestParam(required = false) String nombre,
                                 @RequestParam(required = false) String correo,
                                 @RequestParam(required = false) String cedula,
                                 Model model) {
        Specification<Usuario> spec = (root, query, cb) -> {
            var p = cb.conjunction();
            p.getExpressions().add(cb.equal(root.get("rol"), Rol.CLIENTE));
            if (nombre != null && !nombre.isBlank())
                p.getExpressions().add(cb.like(cb.lower(root.get("nombreCompleto")), "%" + nombre.toLowerCase() + "%"));
            if (correo != null && !correo.isBlank())
                p.getExpressions().add(cb.like(cb.lower(root.get("correo")), "%" + correo.toLowerCase() + "%"));
            if (cedula != null && !cedula.isBlank())
                p.getExpressions().add(cb.equal(root.get("cedula"), cedula));
            return p;
        };
        List<Usuario> resultados = usuarioRepositorio.findAll(spec);
        model.addAttribute("resultados", resultados);
        return "trabajador/clientes";
    }

    @GetMapping("/cliente/{id}")
    public String detalleCliente(@PathVariable Long id, Model m) {
        var c = usuarioRepositorio.findById(id).orElseThrow();
        m.addAttribute("c", c);
        m.addAttribute("cuentas", cuentaRepositorio.findByDueno(c));
        m.addAttribute("tarjetas", tarjetaDebitoRepositorio.findByDueno(c));
        m.addAttribute("solicitudes", solicitudProductoRepositorio.findByCliente(c));
        m.addAttribute("tickets", ticketRepositorio.findAll().stream()
                .filter(t -> t.getCliente().getId().equals(id)).toList());
        m.addAttribute("prestamos", prestamoRepositorio.findByCliente(c));
        return "trabajador/cliente-detalle";
    }
}
