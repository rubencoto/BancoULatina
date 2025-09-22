package cr.ac.ulatina.banco.controlador;

import cr.ac.ulatina.banco.entidad.*;
import cr.ac.ulatina.banco.repositorio.TicketRepositorio;
import cr.ac.ulatina.banco.repositorio.UsuarioRepositorio;
import cr.ac.ulatina.banco.servicio.TicketServicio;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TicketControlador {
    private final TicketRepositorio ticketRepositorio;
    private final TicketServicio ticketServicio;
    private final UsuarioRepositorio usuarioRepositorio;

    public TicketControlador(TicketRepositorio t, TicketServicio s, UsuarioRepositorio u) {
        this.ticketRepositorio = t; this.ticketServicio = s; this.usuarioRepositorio = u;
    }

    // Cliente
    @GetMapping("/cliente/tickets")
    public String misTickets(Authentication auth, Model m) {
        var yo = usuarioRepositorio.findByCorreo(auth.getName()).orElseThrow();
        m.addAttribute("tickets", ticketRepositorio.findAll().stream()
                .filter(t -> t.getCliente().getId().equals(yo.getId())).toList());
        return "cliente/tickets";
    }

    @PostMapping("/cliente/tickets")
    public String crearTicket(Authentication auth,
                              @RequestParam String categoria,
                              @RequestParam String descripcion) {
        var yo = usuarioRepositorio.findByCorreo(auth.getName()).orElseThrow();
        ticketServicio.crear(yo, categoria, descripcion);
        return "redirect:/cliente/tickets?creado";
    }

    // Trabajador
    @GetMapping("/trabajador/tickets")
    public String todosTickets(@RequestParam(required=false) EstadoTicket estado, Model m) {
        m.addAttribute("tickets", estado == null ? ticketRepositorio.findAll() : ticketRepositorio.findByEstado(estado));
        m.addAttribute("estado", estado);
        return "trabajador/tickets";
    }

    @PostMapping("/trabajador/tickets/{id}/estado")
    public String actualizarEstado(@PathVariable Long id, @RequestParam EstadoTicket estado, Authentication auth) {
        var tk = ticketRepositorio.findById(id).orElseThrow();
        var trabajador = usuarioRepositorio.findByCorreo(auth.getName()).orElseThrow();
        tk.setAsignadoA(trabajador);
        tk.setEstado(estado);
        ticketRepositorio.save(tk);
        return "redirect:/trabajador/tickets?actualizado";
    }
}
