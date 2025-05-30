package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public String scriptEmail = "";
	
	public void enviarEmail(String para, String assunto, String corpo) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		
		mensagem.setTo(para);
		mensagem.setSubject(assunto);
		mensagem.setText(corpo);
		mailSender.send(mensagem);
	}

}
