package cr.ac.ulatina.banco.servicio;

import cr.ac.ulatina.banco.entidad.*;
import cr.ac.ulatina.banco.repositorio.AsientoContableRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AsientoServicio {
    private final AsientoContableRepositorio asientoRepo;

    public AsientoServicio(AsientoContableRepositorio asientoRepo) {
        this.asientoRepo = asientoRepo;
    }

    @Transactional
    public AsientoContable crearAsiento(String descripcion, List<AsientoLinea> lineas) {
        BigDecimal debe = lineas.stream().map(AsientoLinea::getDebe).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal haber = lineas.stream().map(AsientoLinea::getHaber).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (debe.compareTo(haber) != 0)
            throw new IllegalArgumentException("El asiento no cuadra (debe != haber)");

        AsientoContable a = AsientoContable.builder()
                .descripcion(descripcion)
                .totalDebe(debe)
                .totalHaber(haber)
                .build();
        lineas.forEach(l -> l.setAsiento(a));
        a.setLineas(lineas);
        return asientoRepo.save(a);
    }
}
