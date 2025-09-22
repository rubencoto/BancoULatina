package cr.ac.ulatina.banco.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FilaAmortizacion(
        int numeroMes,
        LocalDate fechaVencimiento,
        BigDecimal cuota,
        BigDecimal interes,
        BigDecimal abonoPrincipal,
        BigDecimal saldo
) {}
