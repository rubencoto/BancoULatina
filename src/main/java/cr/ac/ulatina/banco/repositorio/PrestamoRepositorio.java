package cr.ac.ulatina.banco.repositorio;

import cr.ac.ulatina.banco.entidad.Prestamo;
import cr.ac.ulatina.banco.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByCliente(Usuario cliente);
}
