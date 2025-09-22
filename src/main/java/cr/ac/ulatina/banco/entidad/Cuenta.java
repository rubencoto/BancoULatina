package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name = "cuentas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cuenta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario dueno;

    @Enumerated(EnumType.STRING)
    private Moneda moneda = Moneda.CRC;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipo = TipoCuenta.AHORROS;

    @Column(precision = 18, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    private boolean activa = true; // (puedes mantener por compatibilidad)

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado = EstadoCuenta.ACTIVA;

    // Interés anual configurable por cuenta (p. ej. 0.01 = 1% anual)
    @Column(precision = 8, scale = 4)
    private BigDecimal tasaInteresAnual = BigDecimal.ZERO;
}
