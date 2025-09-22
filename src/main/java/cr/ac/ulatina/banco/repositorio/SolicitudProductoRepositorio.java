package cr.ac.ulatina.banco.repositorio;

import cr.ac.ulatina.banco.entidad.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudProductoRepositorio extends JpaRepository<SolicitudProducto, Long> {
    List<SolicitudProducto> findByCliente(Usuario cliente);
}
