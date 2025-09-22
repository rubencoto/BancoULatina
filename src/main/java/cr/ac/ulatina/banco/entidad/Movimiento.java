package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "movimientos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Movimiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private Cuenta cuenta;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipo;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(length = 100)
    private String referencia; // p.ej. "SINPE: 123-ABC", "Transferencia interna #id"

    private LocalDateTime creadoEn = LocalDateTime.now();
}
