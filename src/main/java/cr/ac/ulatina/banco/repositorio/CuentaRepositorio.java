package cr.ac.ulatina.banco.repositorio;

import cr.ac.ulatina.banco.entidad.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import java.util.*;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByDueno(Usuario dueno);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Cuenta c where c.id = :id")
    Optional<Cuenta> bloquearPorId(Long id);
}
