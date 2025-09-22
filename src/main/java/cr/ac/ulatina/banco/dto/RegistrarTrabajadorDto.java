package cr.ac.ulatina.banco.dto;

import jakarta.validation.constraints.*;

public record RegistrarTrabajadorDto(
        @NotBlank String nombreCompleto,
        @Email String correo,
        @NotBlank String cedula,
        @Size(min = 8) String contrasena
) {}
