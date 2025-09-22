package cr.ac.ulatina.banco.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario cliente;

    @ManyToOne
    private Usuario asignadoA;

    @Enumerated(EnumType.STRING)
    private EstadoTicket estado = EstadoTicket.ABIERTO;

    @Column(length = 100)
    private String categoria;

    @Column(length = 2000)
    private String descripcion;

    private LocalDateTime creadoEn = LocalDateTime.now();
    private LocalDateTime actualizadoEn = LocalDateTime.now();
}
