package cr.ac.ulatina.banco.servicio;

import org.springframework.stereotype.Service;

@Service
public class PlantillaCorreoServicio {

    public String crearPlantillaBienvenida(String nombreCliente, String nombreBanco) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Bienvenido a %s</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px;">
                <div style="background: linear-gradient(135deg, #1e3c72 0%%, #2a5298 100%%); padding: 30px; text-align: center; border-radius: 10px 10px 0 0;">
                    <h1 style="color: white; margin: 0; font-size: 28px;">¡Bienvenido a %s!</h1>
                </div>
                
                <div style="background-color: #f8f9fa; padding: 30px; border-radius: 0 0 10px 10px; border: 1px solid #e9ecef;">
                    <h2 style="color: #2a5298; margin-top: 0;">Estimado/a %s,</h2>
                    
                    <p style="font-size: 16px; margin-bottom: 20px;">
                        ¡Felicitaciones! Su cuenta ha sido creada exitosamente en <strong>%s</strong>.
                    </p>
                    
                    <div style="background-color: white; padding: 20px; border-radius: 8px; border-left: 4px solid #28a745; margin: 20px 0;">
                        <h3 style="color: #28a745; margin-top: 0;">Servicios disponibles:</h3>
                        <ul style="color: #666; padding-left: 20px;">
                            <li>Cuentas de ahorro y corriente</li>
                            <li>Transferencias nacionales e internacionales</li>
                            <li>Préstamos personales</li>
                            <li>Tarjetas de débito</li>
                            <li>Banca en línea 24/7</li>
                        </ul>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="#" style="background-color: #007bff; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">
                            Acceder a Mi Cuenta
                        </a>
                    </div>
                    
                    <p style="color: #666; font-size: 14px; margin-top: 30px;">
                        Si tiene alguna consulta, no dude en contactarnos:<br>
                        📧 Email: soporte@bancoulatina.cr<br>
                        📞 Teléfono: (506) 2000-1234<br>
                        🕐 Horario: Lunes a viernes de 8:00 AM a 6:00 PM
                    </p>
                    
                    <hr style="border: none; border-top: 1px solid #ddd; margin: 30px 0;">
                    
                    <p style="text-align: center; color: #888; font-size: 12px;">
                        Saludos cordiales,<br>
                        <strong>Equipo de %s</strong><br>
                        <em>Su banco de confianza</em>
                    </p>
                </div>
            </body>
            </html>
            """, nombreBanco, nombreBanco, nombreCliente, nombreBanco, nombreBanco);
    }

    public String crearPlantillaTransaccion(String tipoTransaccion, String monto, String tarjeta, String fecha, String nombreBanco) {
        String colorTipo = tipoTransaccion.toLowerCase().contains("depósito") ? "#28a745" : "#dc3545";
        String iconoTipo = tipoTransaccion.toLowerCase().contains("depósito") ? "📈" : "💸";

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Alerta de Transacción - %s</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px;">
                <div style="background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 10px; overflow: hidden;">
                    
                    <div style="background-color: %s; padding: 20px; text-align: center;">
                        <h1 style="color: white; margin: 0; font-size: 24px;">%s Alerta de Transacción</h1>
                    </div>
                    
                    <div style="padding: 30px;">
                        <h2 style="color: #333; margin-top: 0;">Estimado cliente,</h2>
                        
                        <p style="font-size: 16px; margin-bottom: 25px;">
                            Se ha realizado una transacción en su cuenta. A continuación los detalles:
                        </p>
                        
                        <div style="background-color: white; border: 2px solid %s; border-radius: 8px; padding: 25px; margin: 20px 0;">
                            <table style="width: 100%%; border-collapse: collapse;">
                                <tr>
                                    <td style="padding: 10px; border-bottom: 1px solid #eee; font-weight: bold; width: 30%%;">Tipo:</td>
                                    <td style="padding: 10px; border-bottom: 1px solid #eee; color: %s; font-weight: bold;">%s</td>
                                </tr>
                                <tr>
                                    <td style="padding: 10px; border-bottom: 1px solid #eee; font-weight: bold;">Monto:</td>
                                    <td style="padding: 10px; border-bottom: 1px solid #eee; font-size: 18px; font-weight: bold;">%s</td>
                                </tr>
                                <tr>
                                    <td style="padding: 10px; border-bottom: 1px solid #eee; font-weight: bold;">Tarjeta:</td>
                                    <td style="padding: 10px; border-bottom: 1px solid #eee;">%s</td>
                                </tr>
                                <tr>
                                    <td style="padding: 10px; font-weight: bold;">Fecha:</td>
                                    <td style="padding: 10px;">%s</td>
                                </tr>
                            </table>
                        </div>
                        
                        <div style="background-color: #fff3cd; border: 1px solid #ffeaa7; border-radius: 5px; padding: 15px; margin: 20px 0;">
                            <p style="margin: 0; color: #856404;">
                                <strong>⚠️ Importante:</strong> Si no reconoce esta transacción, contacte inmediatamente a nuestro servicio al cliente.
                            </p>
                        </div>
                        
                        <div style="text-align: center; margin: 25px 0;">
                            <a href="#" style="background-color: #dc3545; color: white; padding: 10px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block; margin-right: 10px;">
                                Reportar Problema
                            </a>
                            <a href="#" style="background-color: #6c757d; color: white; padding: 10px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">
                                Ver Estado de Cuenta
                            </a>
                        </div>
                        
                        <hr style="border: none; border-top: 1px solid #ddd; margin: 30px 0;">
                        
                        <p style="text-align: center; color: #888; font-size: 12px;">
                            Saludos cordiales,<br>
                            <strong>%s</strong><br>
                            Equipo de Seguridad
                        </p>
                    </div>
                </div>
            </body>
            </html>
            """, nombreBanco, colorTipo, iconoTipo, colorTipo, colorTipo, tipoTransaccion, monto, tarjeta, fecha, nombreBanco);
    }

    public String crearPlantillaSaldoBajo(String saldoActual, String limiteMinimo, String nombreBanco) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Alerta de Saldo Bajo - %s</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px;">
                <div style="background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 10px; overflow: hidden;">
                    
                    <div style="background-color: #ffc107; padding: 20px; text-align: center;">
                        <h1 style="color: #212529; margin: 0; font-size: 24px;">⚠️ Alerta de Saldo Bajo</h1>
                    </div>
                    
                    <div style="padding: 30px;">
                        <h2 style="color: #333; margin-top: 0;">Estimado cliente,</h2>
                        
                        <p style="font-size: 16px; margin-bottom: 25px;">
                            Su cuenta presenta un saldo por debajo del límite recomendado.
                        </p>
                        
                        <div style="background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 20px; margin: 20px 0;">
                            <table style="width: 100%%; border-collapse: collapse;">
                                <tr>
                                    <td style="padding: 10px; font-weight: bold; width: 40%%;">Saldo actual:</td>
                                    <td style="padding: 10px; font-size: 20px; font-weight: bold; color: #dc3545;">%s</td>
                                </tr>
                                <tr>
                                    <td style="padding: 10px; font-weight: bold;">Límite mínimo recomendado:</td>
                                    <td style="padding: 10px; font-size: 16px; color: #28a745;">%s</td>
                                </tr>
                            </table>
                        </div>
                        
                        <div style="background-color: #d1ecf1; border: 1px solid #bee5eb; border-radius: 5px; padding: 15px; margin: 20px 0;">
                            <h3 style="color: #0c5460; margin-top: 0;">💡 Recomendaciones:</h3>
                            <ul style="color: #0c5460; padding-left: 20px;">
                                <li>Realice un depósito para evitar cargos por saldo insuficiente</li>
                                <li>Configure alertas de saldo para estar informado</li>
                                <li>Considere programar depósitos automáticos</li>
                            </ul>
                        </div>
                        
                        <div style="text-align: center; margin: 25px 0;">
                            <a href="#" style="background-color: #28a745; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block; margin-right: 10px;">
                                Realizar Depósito
                            </a>
                            <a href="#" style="background-color: #007bff; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">
                                Ver Mi Cuenta
                            </a>
                        </div>
                        
                        <hr style="border: none; border-top: 1px solid #ddd; margin: 30px 0;">
                        
                        <p style="text-align: center; color: #888; font-size: 12px;">
                            Saludos cordiales,<br>
                            <strong>%s</strong><br>
                            Equipo de Gestión de Cuentas
                        </p>
                    </div>
                </div>
            </body>
            </html>
            """, nombreBanco, saldoActual, limiteMinimo, nombreBanco);
    }
}
