package cr.ac.ulatina.banco.repositorio;

import cr.ac.ulatina.banco.entidad.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TarjetaDebitoRepositorio extends JpaRepository<TarjetaDebito, Long> {
    List<TarjetaDebito> findByDueno(Usuario dueno);
}
