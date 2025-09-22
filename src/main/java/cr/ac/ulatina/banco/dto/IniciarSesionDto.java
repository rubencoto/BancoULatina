package cr.ac.ulatina.banco.dto;

import jakarta.validation.constraints.*;

public record IniciarSesionDto(
        @Email String correo,
        @NotBlank String contrasena
) {}
