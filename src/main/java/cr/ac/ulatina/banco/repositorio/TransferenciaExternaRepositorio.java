package cr.ac.ulatina.banco.repositorio;

import cr.ac.ulatina.banco.entidad.TransferenciaExterna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaExternaRepositorio extends JpaRepository<TransferenciaExterna, Long> {
}
