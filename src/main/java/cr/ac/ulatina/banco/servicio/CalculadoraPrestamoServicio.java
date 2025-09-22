package cr.ac.ulatina.banco.servicio;

import cr.ac.ulatina.banco.dto.FilaAmortizacion;
import cr.ac.ulatina.banco.dto.PrestamoCalculadoraDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculadoraPrestamoServicio {

    public record Resultado(BigDecimal cuotaMensual, BigDecimal pagoTotal, BigDecimal interesTotal, List<FilaAmortizacion> cronograma) {}

    public Resultado calcular(PrestamoCalculadoraDto req) {
        BigDecimal principal = req.getMonto();
        BigDecimal tasaAnual = req.getTasaAnual();
        int n = req.getPlazoMeses();

        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0) principal = BigDecimal.ZERO;
        if (tasaAnual == null) tasaAnual = BigDecimal.ZERO;
        if (n <= 0) n = 1;

        BigDecimal r = tasaAnual.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        BigDecimal cuota;

        if (r.compareTo(BigDecimal.ZERO) == 0) {
            cuota = principal.divide(BigDecimal.valueOf(n), 2, RoundingMode.HALF_UP);
        } else {
            BigDecimal unoMasR = BigDecimal.ONE.add(r);
            BigDecimal pow = unoMasR.pow(n);
            BigDecimal denom = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(pow, 10, RoundingMode.HALF_UP));
            cuota = r.multiply(principal).divide(denom, 2, RoundingMode.HALF_UP);
        }

        List<FilaAmortizacion> filas = new ArrayList<>();
        BigDecimal saldo = principal;
        BigDecimal pagoTotal = BigDecimal.ZERO;
        BigDecimal interesTotal = BigDecimal.ZERO;
        LocalDate venc = LocalDate.now().plusMonths(1);

        for (int i = 1; i <= n; i++) {
            BigDecimal interes = saldo.multiply(r).setScale(2, RoundingMode.HALF_UP);
            BigDecimal abono = cuota.subtract(interes).setScale(2, RoundingMode.HALF_UP);
            if (i == n) { // ajuste final
                abono = saldo;
                cuota = abono.add(interes).setScale(2, RoundingMode.HALF_UP);
            }
            saldo = saldo.subtract(abono).setScale(2, RoundingMode.HALF_UP);
            filas.add(new FilaAmortizacion(i, venc, cuota, interes, abono, saldo));
            pagoTotal = pagoTotal.add(cuota);
            interesTotal = interesTotal.add(interes);
            venc = venc.plusMonths(1);
        }

        return new Resultado(cuota, pagoTotal, interesTotal, filas);
    }
}
