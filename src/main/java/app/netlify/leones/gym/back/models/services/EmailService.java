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

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Usuario;

@Service
@Transactional
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String email;
	
	
	public void sendListEmail(Cliente cliente, String image, String numControl) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(cliente.getCorreo());
			helper.setSubject("Bienvenido " + cliente.getNombre() + " a Leones Gym!" );
			helper.setText("Su registro ha sido exitoso");
			helper.setText("Su numero de control es: " + numControl + " tambien encontraras un codigo qr para tu ingreso.");
			FileSystemResource file = new FileSystemResource(new File(image));
			helper.addAttachment(image, file);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void reenviarQREmail(Cliente cliente, String image) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(cliente.getCorreo());
			helper.setSubject("Hola " + cliente.getNombre() + " has solicitado un reenvio de tu QR!" );
			helper.setText("Tu numero de control es: " + cliente.getApellidos() + " tambien encontraras un codigo qr para tu ingreso.");
			FileSystemResource file = new FileSystemResource(new File(image));
			helper.addAttachment(image, file);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void recuperContraseñaEmail(Usuario usuario) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email);
			helper.setTo(usuario.getEmail());
			helper.setSubject("Hola " + usuario.getUsername() + " has solicitado un reseteo de contraseña!" );
			helper.setText("Favor de copiar el siguiente elance en tu navegador: http://localhost:8090/leonesgym-front/#/recupera/pass/" + usuario.getId());

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
			helper.setTo(email);
			helper.setSubject("Se ha registrado un nuevo ingreso");
			helper.setText("Ha ingresado el cliente: " + nombre + " con estatus: " + estatus + " y numero de control: " + numControl);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
