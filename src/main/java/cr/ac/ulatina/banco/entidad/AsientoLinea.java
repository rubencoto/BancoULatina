package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name = "asientos_lineas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AsientoLinea {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private AsientoContable asiento;

    // Código contable simple (ej: "101-Caja", "110-Clientes")
    @Column(length = 40, nullable = false)
    private String cuentaContable;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal debe = BigDecimal.ZERO;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal haber = BigDecimal.ZERO;
}
