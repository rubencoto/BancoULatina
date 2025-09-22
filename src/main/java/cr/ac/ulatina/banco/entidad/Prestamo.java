package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "prestamos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Prestamo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario cliente;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal montoPrincipal;

    @Column(precision = 8, scale = 4, nullable = false)
    private BigDecimal tasaAnual;

    @Column(nullable = false)
    private Integer plazoMeses;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal saldoPendiente;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoPrestamo estado = EstadoPrestamo.PENDIENTE_DESEMBOLSO;

    @Builder.Default
    private LocalDateTime creadoEn = LocalDateTime.now();
}
