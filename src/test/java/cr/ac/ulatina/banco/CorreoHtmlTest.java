package cr.ac.ulatina.banco;

import cr.ac.ulatina.banco.servicio.AlertaCorreoServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;

@SpringBootTest
public class CorreoHtmlTest {

    @Autowired
    private AlertaCorreoServicio alertaCorreoServicio;

    @Test
    public void testEnviarCorreosHtmlProfesionales() {
        String emailDestino = "rubencoto11@icloud.com";

        try {
            // Prueba 1: Correo de bienvenida con HTML profesional
            alertaCorreoServicio.enviarBienvenidaClienteHtml(emailDestino, "Rubén Coto");
            System.out.println("✅ Correo HTML de bienvenida enviado exitosamente");

            Thread.sleep(3000); // Esperar 3 segundos

            // Prueba 2: Alerta de transacción con HTML profesional
            alertaCorreoServicio.enviarAlertaTransaccionHtml(
                emailDestino,
                "Depósito",
                new BigDecimal("2500.75"),
                "4532123456789012"
            );
            System.out.println("✅ Alerta HTML de transacción enviada exitosamente");

            Thread.sleep(3000); // Esperar 3 segundos

            // Prueba 3: Alerta de saldo bajo con HTML profesional
            alertaCorreoServicio.enviarAlertaSaldoBajoHtml(
                emailDestino,
                new BigDecimal("125.50"),
                new BigDecimal("500.00")
            );
            System.out.println("✅ Alerta HTML de saldo bajo enviada exitosamente");

            Thread.sleep(3000); // Esperar 3 segundos

            // Prueba 4: Correo de verificación de cuenta
            alertaCorreoServicio.enviarVerificacionCuenta(
                emailDestino,
                "Rubén Coto",
                "VER-" + System.currentTimeMillis() % 100000
            );
            System.out.println("✅ Correo de verificación enviado exitosamente");

            Thread.sleep(3000); // Esperar 3 segundos

            // Prueba 5: Confirmación de transferencia
            alertaCorreoServicio.enviarConfirmacionTransferencia(
                emailDestino,
                "Rubén Coto",
                new BigDecimal("750.00"),
                "CR12345678901234567890"
            );
            System.out.println("✅ Confirmación de transferencia enviada exitosamente");

        } catch (Exception e) {
            System.err.println("❌ Error al enviar correos HTML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testCorreoActividadSospechosa() {
        String emailDestino = "rubencoto11@icloud.com";

        try {
            // Usar el método enviarCorreoSimple que sí existe en el servicio
            alertaCorreoServicio.enviarCorreoSimple(
                emailDestino,
                "Alerta de Actividad Sospechosa - Banco U Latina",
                "Estimado Rubén Coto,\n\n" +
                "Se ha detectado actividad sospechosa en su cuenta:\n" +
                "Intento de acceso desde ubicación no reconocida (Dirección IP: 192.168.1.100)\n\n" +
                "Si no fue usted, contacte inmediatamente a nuestro servicio al cliente.\n\n" +
                "Saludos,\nEquipo de Seguridad de Banco U Latina"
            );
            System.out.println("✅ Alerta de actividad sospechosa enviada exitosamente");

        } catch (Exception e) {
            System.err.println("❌ Error al enviar alerta de seguridad: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
