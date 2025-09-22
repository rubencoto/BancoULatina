package cr.ac.ulatina.banco.repositorio;

import cr.ac.ulatina.banco.entidad.Usuario;
import cr.ac.ulatina.banco.entidad.Rol;
import org.springframework.data.jpa.repository.*;
import java.util.*;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByRol(Rol rol);
    Optional<Usuario> findByCedula(String cedula);
}

