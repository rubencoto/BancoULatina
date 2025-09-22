package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "transferencias_externas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransferenciaExterna {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private Cuenta cuentaOrigen;

    @Column(length = 30, nullable = false)
    private String bancoDestino; // código, p.ej. "BNCR" o IBAN

    @Column(length = 64, nullable = false)
    private String cuentaDestino;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(length = 50, unique = true)
    private String comprobante; // único

    private LocalDateTime creadoEn = LocalDateTime.now();
}
