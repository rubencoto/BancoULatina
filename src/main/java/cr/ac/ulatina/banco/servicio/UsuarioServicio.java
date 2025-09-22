package cr.ac.ulatina.banco.servicio;

import cr.ac.ulatina.banco.dto.*;
import cr.ac.ulatina.banco.entidad.*;
import cr.ac.ulatina.banco.repositorio.UsuarioRepositorio;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder encoder;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder encoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.encoder = encoder;
    }

    public Usuario registrarTrabajador(RegistrarTrabajadorDto dto) {
        Usuario u = Usuario.builder()
                .nombreCompleto(dto.nombreCompleto())
                .correo(dto.correo())
                .cedula(dto.cedula())
                .hashContrasena(encoder.encode(dto.contrasena()))
                .rol(Rol.TRABAJADOR)
                .build();
        return usuarioRepositorio.save(u);
    }

    public Usuario registrarCliente(RegistrarClienteDto dto) {
        Usuario u = Usuario.builder()
                .nombreCompleto(dto.nombreCompleto())
                .correo(dto.correo())
                .cedula(dto.cedula())
                .hashContrasena(encoder.encode(dto.contrasena()))
                .rol(Rol.CLIENTE)
                .build();
        return usuarioRepositorio.save(u);
    }
}


