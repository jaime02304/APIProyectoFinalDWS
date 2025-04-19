package edu.proyectoFinalAPI.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Esta clase esta relacionada con el envio de correo electronicos
 * 
 * @author jpribio - 19/04/25
 */
@Service
public class EmailServicios {

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Metodo para enviar el correo electronico al usuario
	 * 
	 * @author jpribio - 19/04/25
	 * @param toEmail
	 * @param token
	 */
	public void enviarVerificacioEmail(String toEmail, String token) {
		String ruta = "https://maggot-glad-chipmunk.ngrok-free.app/api/usuario/verificar?token=" + token;
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(toEmail);
		msg.setSubject("Verifica tu cuenta");
		msg.setText("Hola,\n\nHaz clic para verificar:\n" + ruta);
		msg.setFrom("TU_CORREO@gmail.com");
		mailSender.send(msg);
	}
}
