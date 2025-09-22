package cr.ac.ulatina.banco.repositorio;

import cr.ac.ulatina.banco.entidad.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepositorio extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEstado(EstadoTicket estado);
}
