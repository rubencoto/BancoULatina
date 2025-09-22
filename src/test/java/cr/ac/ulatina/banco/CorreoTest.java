package cr.ac.ulatina.banco;

import cr.ac.ulatina.banco.servicio.AlertaCorreoServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class CorreoTest {

    @Autowired
    private AlertaCorreoServicio alertaCorreoServicio;

    @Test
    public void testEnviarCorreoPrueba() {
        // Enviar correo de prueba real
        String emailDestino = "rubencoto11@icloud.com";

        try {
            // Prueba 1: Correo de bienvenida
            alertaCorreoServicio.enviarBienvenidaCliente(emailDestino, "Rubén Coto");
            System.out.println("✅ Correo de bienvenida enviado exitosamente a: " + emailDestino);

            // Esperar un poco antes del siguiente correo
            Thread.sleep(2000);

            // Prueba 2: Alerta de transacción
            alertaCorreoServicio.enviarAlertaTransaccion(
                    emailDestino,
                    "Depósito",
                    new BigDecimal("1500.50"),
                    "1234567890123456"
            );
            System.out.println("✅ Alerta de transacción enviada exitosamente a: " + emailDestino);

            // Esperar un poco antes del siguiente correo
            Thread.sleep(2000);

            // Prueba 3: Alerta de saldo bajo
            alertaCorreoServicio.enviarAlertaSaldoBajo(
                    emailDestino,
                    new BigDecimal("250.00"),
                    new BigDecimal("500.00")
            );
            System.out.println("✅ Alerta de saldo bajo enviada exitosamente a: " + emailDestino);

        } catch (Exception e) {
            System.err.println("❌ Error al enviar correos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testEnviarCorreoSimple() {
        String emailDestino = "rubencoto11@icloud.com";

        try {
            alertaCorreoServicio.enviarCorreoSimple(
                    emailDestino,
                    "Prueba de Configuración SMTP - Banco U Latina",
                    "¡Hola Rubén!\n\n" +
                            "Este es un correo de prueba para verificar que la configuración SMTP está funcionando correctamente.\n\n" +
                            "Configuración utilizada:\n" +
                            "- Host: smtp.gmail.com\n" +
                            "- Puerto: 587\n" +
                            "- Usuario: serviciocontactoventaonline@gmail.com\n" +
                            "- Seguridad: TLS/STARTTLS\n\n" +
                            "Si recibes este correo, significa que todo está funcionando perfectamente.\n\n" +
                            "Saludos cordiales,\n" +
                            "Sistema Banco U Latina\n" +
                            "Fecha: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            );
            System.out.println("✅ Correo simple de prueba enviado exitosamente a: " + emailDestino);

        } catch (Exception e) {
            System.err.println("❌ Error al enviar correo simple: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
