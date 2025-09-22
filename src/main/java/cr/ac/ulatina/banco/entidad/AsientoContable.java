package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "asientos_contables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsientoContable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String descripcion;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal totalDebe = BigDecimal.ZERO;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal totalHaber = BigDecimal.ZERO;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToMany(mappedBy = "asiento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AsientoLinea> lineas;

    // Método de conveniencia para verificar que el asiento cuadre
    public boolean cuadra() {
        return totalDebe.compareTo(totalHaber) == 0;
    }
}
