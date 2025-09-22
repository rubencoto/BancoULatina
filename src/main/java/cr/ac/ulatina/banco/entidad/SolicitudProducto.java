package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "solicitudes_producto")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SolicitudProducto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario cliente;

    @Enumerated(EnumType.STRING)
    private TipoProducto tipo;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    // Para PRESTAMO (opcionales)
    @Column(precision = 18, scale = 2)
    private BigDecimal monto;
    private Integer plazoMeses;
    @Column(precision = 8, scale = 4)
    private BigDecimal tasaAnual;

    private LocalDateTime creadoEn = LocalDateTime.now();
}
