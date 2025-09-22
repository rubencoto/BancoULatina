package cr.ac.ulatina.banco.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cr.ac.ulatina.banco.repositorio.CuentaRepositorio;
import cr.ac.ulatina.banco.entidad.Cuenta;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AlertaAutomaticaServicio {

    private static final Logger logger = LoggerFactory.getLogger(AlertaAutomaticaServicio.class);

    @Autowired
    private AlertaCorreoServicio alertaCorreoServicio;

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    private static final BigDecimal LIMITE_SALDO_BAJO = new BigDecimal("500.00");

    /**
     * Verifica saldos bajos cada día a las 8:00 AM
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void verificarSaldosBajos() {
        logger.info("Iniciando verificación automática de saldos bajos");

        try {
            List<Cuenta> cuentasActivas = cuentaRepositorio.findByActivaTrue();
            int alertasEnviadas = 0;

            for (Cuenta cuenta : cuentasActivas) {
                if (cuenta.getSaldo() != null &&
                    cuenta.getSaldo().compareTo(LIMITE_SALDO_BAJO) < 0 &&
                    cuenta.getDueno() != null &&
                    cuenta.getDueno().getCorreo() != null) {

                    alertaCorreoServicio.enviarAlertaSaldoBajo(
                        cuenta.getDueno().getCorreo(),
                        cuenta.getSaldo(),
                        LIMITE_SALDO_BAJO
                    );

                    alertasEnviadas++;
                }
            }

            logger.info("Verificación de saldos completada. Alertas enviadas: {}", alertasEnviadas);

        } catch (Exception e) {
            logger.error("Error en verificación automática de saldos: {}", e.getMessage());
        }
    }

    /**
     * Envía recordatorios de préstamos vencidos cada lunes a las 9:00 AM
     */
    @Scheduled(cron = "0 0 9 * * MON")
    public void recordatorioPrestamos() {
        logger.info("Enviando recordatorios de préstamos");
        // Aquí se implementaría la lógica cuando tengamos el repositorio de préstamos
    }

    /**
     * Método manual para probar las alertas automáticas
     */
    public void ejecutarVerificacionManual() {
        logger.info("Ejecutando verificación manual de saldos bajos");
        verificarSaldosBajos();
    }
}
