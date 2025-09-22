package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombreCompleto;

    @Email @Column(unique = true, nullable = false)
    private String correo;

    @Column(unique = true, nullable = false)
    private String cedula;

    @NotBlank
    private String hashContrasena;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private LocalDateTime creadoEn = LocalDateTime.now();
    private boolean habilitado = true;
}
