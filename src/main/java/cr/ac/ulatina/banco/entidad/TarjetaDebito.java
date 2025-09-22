package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "tarjetas_debito")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TarjetaDebito {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario dueno;

    @ManyToOne(optional = false)
    private Cuenta cuentaAsociada;

    @Column(unique = true, nullable = false, length = 16)
    private String numeroTarjeta;

    private boolean activa = true;
}
