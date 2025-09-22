package cr.ac.ulatina.banco.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;

@Service
public class AlertaCorreoServicio {

    private static final Logger logger = LoggerFactory.getLogger(AlertaCorreoServicio.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PlantillaCorreoServicio plantillaCorreoServicio;

    @Value("${spring.mail.username:serviciocontactoventaonline@gmail.com}")
    private String fromEmail;

    @Value("${app.nombre:Banco U Latina}")
    private String nombreBanco;

    /**
     * Envía un correo simple de texto plano
     */
    public void enviarCorreoSimple(String destinatario, String asunto, String mensaje) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(destinatario);
            mailMessage.setSubject(asunto);
            mailMessage.setText(mensaje);

            mailSender.send(mailMessage);
            logger.info("Correo enviado exitosamente a: {}", destinatario);
        } catch (Exception e) {
            logger.error("Error al enviar correo a {}: {}", destinatario, e.getMessage());
        }
    }

    /**
     * Envía un correo HTML más elaborado
     */
    public void enviarCorreoHtml(String destinatario, String asunto, String contenidoHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);

            mailSender.send(message);
            logger.info("Correo HTML enviado exitosamente a: {}", destinatario);
        } catch (MessagingException e) {
            logger.error("Error al enviar correo HTML a {}: {}", destinatario, e.getMessage());
        }
    }

    /**
     * Alerta de bienvenida para nuevos clientes
     */
    public void enviarBienvenidaCliente(String email, String nombreCliente) {
        String asunto = "Bienvenido a " + nombreBanco;
        String mensaje = String.format(
            "Estimado/a %s,\n\n" +
            "¡Bienvenido/a a %s!\n\n" +
            "Su cuenta ha sido creada exitosamente. Ahora puede acceder a todos nuestros servicios bancarios.\n\n" +
            "Si tiene alguna consulta, no dude en contactarnos.\n\n" +
            "Saludos cordiales,\n" +
            "Equipo de %s",
            nombreCliente, nombreBanco, nombreBanco
        );

        enviarCorreoSimple(email, asunto, mensaje);
    }

    /**
     * Alerta por transacción realizada
     */
    public void enviarAlertaTransaccion(String email, String tipoTransaccion, BigDecimal monto, String numeroTarjeta) {
        String asunto = "Alerta de Transacción - " + nombreBanco;
        String mensaje = String.format(
            "Estimado cliente,\n\n" +
            "Se ha realizado una transacción en su cuenta:\n\n" +
            "Tipo: %s\n" +
            "Monto: ₡%.2f\n" +
            "Tarjeta: ****%s\n" +
            "Fecha: %s\n\n" +
            "Si no reconoce esta transacción, contacte inmediatamente a nuestro servicio al cliente.\n\n" +
            "Saludos,\n" +
            "%s",
            tipoTransaccion,
            monto,
            numeroTarjeta.substring(Math.max(0, numeroTarjeta.length() - 4)),
            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            nombreBanco
        );

        enviarCorreoSimple(email, asunto, mensaje);
    }

    /**
     * Alerta de saldo bajo
     */
    public void enviarAlertaSaldoBajo(String email, BigDecimal saldoActual, BigDecimal limiteMinimo) {
        String asunto = "Alerta de Saldo Bajo - " + nombreBanco;
        String mensaje = String.format(
            "Estimado cliente,\n\n" +
            "Su cuenta presenta un saldo bajo:\n\n" +
            "Saldo actual: ₡%.2f\n" +
            "Límite mínimo: ₡%.2f\n\n" +
            "Le recomendamos realizar un depósito para evitar cargos por saldo insuficiente.\n\n" +
            "Saludos,\n" +
            "%s",
            saldoActual, limiteMinimo, nombreBanco
        );

        enviarCorreoSimple(email, asunto, mensaje);
    }

    /**
     * Alerta de bienvenida para nuevos clientes con HTML
     */
    public void enviarBienvenidaClienteHtml(String email, String nombreCliente) {
        String asunto = "Bienvenido a " + nombreBanco;
        String contenidoHtml = plantillaCorreoServicio.crearPlantillaBienvenida(nombreCliente, nombreBanco);

        enviarCorreoHtml(email, asunto, contenidoHtml);
    }

    /**
     * Alerta por transacción realizada con HTML
     */
    public void enviarAlertaTransaccionHtml(String email, String tipoTransaccion, BigDecimal monto, String numeroTarjeta) {
        String asunto = "Alerta de Transacción - " + nombreBanco;
        String montoFormateado = String.format("₡%.2f", monto);
        String tarjetaOculta = "****" + numeroTarjeta.substring(Math.max(0, numeroTarjeta.length() - 4));
        String fecha = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        String contenidoHtml = plantillaCorreoServicio.crearPlantillaTransaccion(
            tipoTransaccion, montoFormateado, tarjetaOculta, fecha, nombreBanco
        );

        enviarCorreoHtml(email, asunto, contenidoHtml);
    }

    /**
     * Alerta de saldo bajo con HTML
     */
    public void enviarAlertaSaldoBajoHtml(String email, BigDecimal saldoActual, BigDecimal limiteMinimo) {
        String asunto = "Alerta de Saldo Bajo - " + nombreBanco;
        String saldoFormateado = String.format("₡%.2f", saldoActual);
        String limiteFormateado = String.format("₡%.2f", limiteMinimo);

        String contenidoHtml = plantillaCorreoServicio.crearPlantillaSaldoBajo(
            saldoFormateado, limiteFormateado, nombreBanco
        );

        enviarCorreoHtml(email, asunto, contenidoHtml);
    }

    /**
     * Método de conveniencia para enviar correo de verificación de cuenta
     */
    public void enviarVerificacionCuenta(String email, String nombreCliente, String codigoVerificacion) {
        String asunto = "Verificación de Cuenta - " + nombreBanco;
        String mensaje = String.format(
            "Estimado/a %s,\n\n" +
            "Para completar la configuración de su cuenta, ingrese el siguiente código de verificación:\n\n" +
            "CÓDIGO: %s\n\n" +
            "Este código expira en 10 minutos.\n\n" +
            "Si no solicitó esta verificación, ignore este correo.\n\n" +
            "Saludos cordiales,\n" +
            "Equipo de Seguridad de %s",
            nombreCliente, codigoVerificacion, nombreBanco
        );

        enviarCorreoSimple(email, asunto, mensaje);
    }

    /**
     * Confirmación de transferencia
     */
    public void enviarConfirmacionTransferencia(String email, String nombreCliente, BigDecimal monto, String cuentaDestino) {
        String asunto = "Confirmación de Transferencia - " + nombreBanco;
        String mensaje = String.format(
            "Estimado/a %s,\n\n" +
            "Su transferencia ha sido procesada exitosamente:\n\n" +
            "Monto: ₡%.2f\n" +
            "Cuenta destino: %s\n" +
            "Fecha: %s\n" +
            "Referencia: TRF-%d\n\n" +
            "El dinero estará disponible en la cuenta destino en las próximas horas.\n\n" +
            "Gracias por usar nuestros servicios.\n\n" +
            "Saludos cordiales,\n" +
            "%s",
            nombreCliente,
            monto,
            cuentaDestino,
            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            System.currentTimeMillis() % 1000000,
            nombreBanco
        );

        enviarCorreoSimple(email, asunto, mensaje);
    }
}
