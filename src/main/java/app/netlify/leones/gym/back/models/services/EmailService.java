package app.netlify.leones.gym.back.models.services;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String email;
	
	
	public void sendListEmail(String emailTo, String image, String numControl) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Listado");
			helper.setText("Su registro ha sido exitoso");
			helper.setText("Su numero de control es: " + numControl);
			FileSystemResource file = new FileSystemResource(new File(image));
			helper.addAttachment(image, file);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void sendListEmail(String emailTo, String image) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Listado");
			helper.setText("Estimado cliente");

			FileSystemResource file = new FileSystemResource(new File(image));
			helper.addAttachment(image, file);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendIngresoEmail(String emailTo, String nombre, String estatus, String numControl) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Se ha registrado un nuevo ingreso");
			helper.setText("Ha ingresado el cliente: " + nombre + " con estatus: " + estatus + " y numero de control: " + numControl);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
