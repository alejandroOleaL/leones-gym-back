package app.netlify.leones.gym.back.models.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailService {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String email;
	
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

}
