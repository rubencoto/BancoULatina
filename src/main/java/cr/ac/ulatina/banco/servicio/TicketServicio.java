package cr.ac.ulatina.banco.servicio;

import cr.ac.ulatina.banco.entidad.*;
import cr.ac.ulatina.banco.repositorio.TicketRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketServicio {
    private final TicketRepositorio ticketRepositorio;

    public TicketServicio(TicketRepositorio ticketRepositorio) {
        this.ticketRepositorio = ticketRepositorio;
    }

    public Ticket crear(Usuario cliente, String categoria, String descripcion) {
        Ticket t = Ticket.builder()
                .cliente(cliente)
                .categoria(categoria)
                .descripcion(descripcion)
                .estado(EstadoTicket.ABIERTO)
                .build();
        return ticketRepositorio.save(t);
    }

    public List<Ticket> todos() { return ticketRepositorio.findAll(); }
}
