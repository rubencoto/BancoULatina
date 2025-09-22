package cr.ac.ulatina.banco.controlador;

import cr.ac.ulatina.banco.dto.FilaAmortizacion;
import cr.ac.ulatina.banco.dto.PrestamoCalculadoraDto;
import cr.ac.ulatina.banco.entidad.*;
import cr.ac.ulatina.banco.repositorio.PrestamoRepositorio;
import cr.ac.ulatina.banco.repositorio.UsuarioRepositorio;
import cr.ac.ulatina.banco.servicio.CalculadoraPrestamoServicio;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PrestamoControlador {
    private final PrestamoRepositorio prestamoRepo;
    private final UsuarioRepositorio usuarioRepo;
    private final CalculadoraPrestamoServicio calculadora;

    public PrestamoControlador(PrestamoRepositorio p, UsuarioRepositorio u, CalculadoraPrestamoServicio c) {
        this.prestamoRepo = p; this.usuarioRepo = u; this.calculadora = c;
    }

    // Cliente: ver mis préstamos
    @GetMapping("/cliente/prestamos")
    public String misPrestamos(Authentication auth, Model m) {
        var yo = usuarioRepo.findByCorreo(auth.getName()).orElseThrow();
        m.addAttribute("prestamos", prestamoRepo.findByCliente(yo));
        return "cliente/prestamos";
    }

    // Trabajador: listado de préstamos
    @GetMapping("/trabajador/prestamos")
    public String prestamosTrabajador(@RequestParam(required = false) EstadoPrestamo estado, Model m) {
        var lista = estado == null ? prestamoRepo.findAll()
                : prestamoRepo.findAll().stream().filter(l -> l.getEstado() == estado).toList();
        m.addAttribute("prestamos", lista);
        m.addAttribute("estado", estado);
        return "trabajador/prestamos";
    }

    @PostMapping("/trabajador/prestamos/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, @RequestParam EstadoPrestamo estado) {
        var prestamo = prestamoRepo.findById(id).orElseThrow();
        if (estado == EstadoPrestamo.ACTIVO && prestamo.getEstado() == EstadoPrestamo.PENDIENTE_DESEMBOLSO) {
            prestamo.setFechaInicio(java.time.LocalDate.now());
            prestamo.setFechaFin(java.time.LocalDate.now().plusMonths(prestamo.getPlazoMeses()));
        }
        prestamo.setEstado(estado);
        prestamoRepo.save(prestamo);
        return "redirect:/trabajador/prestamos?actualizado";
    }

    // ===== Calculadora (cliente) =====
    @GetMapping("/cliente/calculadora-prestamo")
    public String formCalculadora(Model m) {
        m.addAttribute("form", new PrestamoCalculadoraDto());
        return "cliente/calculadora-prestamo";
    }

    @PostMapping("/cliente/calculadora-prestamo")
    public String resultadoCalculadora(@ModelAttribute("form") PrestamoCalculadoraDto form, Model m) {
        var res = calculadora.calcular(form);
        m.addAttribute("form", form);
        m.addAttribute("cuotaMensual", res.cuotaMensual());
        m.addAttribute("pagoTotal", res.pagoTotal());
        m.addAttribute("interesTotal", res.interesTotal());
        m.addAttribute("cronograma", res.cronograma());
        return "cliente/calculadora-prestamo";
    }
}
