package cr.ac.ulatina.banco.controlador;

import cr.ac.ulatina.banco.entidad.*;
import cr.ac.ulatina.banco.repositorio.PrestamoRepositorio;
import cr.ac.ulatina.banco.repositorio.SolicitudProductoRepositorio;
import cr.ac.ulatina.banco.repositorio.UsuarioRepositorio;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class SolicitudProductoControlador {
    private final SolicitudProductoRepositorio solicitudRepo;
    private final UsuarioRepositorio usuarioRepo;
    private final PrestamoRepositorio prestamoRepo;

    public SolicitudProductoControlador(SolicitudProductoRepositorio r, UsuarioRepositorio u, PrestamoRepositorio p) {
        this.solicitudRepo = r; this.usuarioRepo = u; this.prestamoRepo = p;
    }

    @GetMapping("/cliente/solicitudes")
    public String misSolicitudes(Authentication auth, Model m) {
        var yo = usuarioRepo.findByCorreo(auth.getName()).orElseThrow();
        m.addAttribute("solicitudes", solicitudRepo.findByCliente(yo));
        m.addAttribute("tipos", TipoProducto.values());
        return "cliente/solicitudes";
    }

    @PostMapping("/cliente/solicitudes")
    public String crear(Authentication auth,
                        @RequestParam TipoProducto tipo,
                        @RequestParam(required = false) BigDecimal monto,
                        @RequestParam(required = false) Integer plazoMeses,
                        @RequestParam(required = false) BigDecimal tasaAnual) {
        var yo = usuarioRepo.findByCorreo(auth.getName()).orElseThrow();
        var sp = SolicitudProducto.builder()
                .cliente(yo)
                .tipo(tipo)
                .estado(EstadoSolicitud.PENDIENTE)
                .build();

        if (tipo == TipoProducto.PRESTAMO) {
            if (monto == null) monto = new BigDecimal("1000000");
            if (plazoMeses == null) plazoMeses = 24;
            if (tasaAnual == null) tasaAnual = new BigDecimal("0.12");
            sp.setMonto(monto);
            sp.setPlazoMeses(plazoMeses);
            sp.setTasaAnual(tasaAnual);
        }

        solicitudRepo.save(sp);
        return "redirect:/cliente/solicitudes?creada";
    }

    @GetMapping("/trabajador/solicitudes")
    public String todas(Model m) {
        m.addAttribute("solicitudes", solicitudRepo.findAll());
        return "trabajador/solicitudes";
    }

    @PostMapping("/trabajador/solicitudes/{id}/estado")
    public String establecer(@PathVariable Long id, @RequestParam EstadoSolicitud estado) {
        var sp = solicitudRepo.findById(id).orElseThrow();
        sp.setEstado(estado);
        solicitudRepo.save(sp);

        if (sp.getTipo() == TipoProducto.PRESTAMO && estado == EstadoSolicitud.APROBADA) {
            var prestamo = Prestamo.builder()
                    .cliente(sp.getCliente())
                    .montoPrincipal(sp.getMonto() != null ? sp.getMonto() : new BigDecimal("1000000"))
                    .tasaAnual(sp.getTasaAnual() != null ? sp.getTasaAnual() : new BigDecimal("0.12"))
                    .plazoMeses(sp.getPlazoMeses() != null ? sp.getPlazoMeses() : 24)
                    .saldoPendiente(sp.getMonto() != null ? sp.getMonto() : new BigDecimal("1000000"))
                    .estado(EstadoPrestamo.PENDIENTE_DESEMBOLSO)
                    .build();
            prestamoRepo.save(prestamo);
        }

        return "redirect:/trabajador/solicitudes?actualizada";
    }
}
