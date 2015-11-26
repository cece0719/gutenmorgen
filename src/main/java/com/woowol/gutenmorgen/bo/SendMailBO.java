package com.woowol.gutenmorgen.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class SendMailBO {
	@Value("${sendmail.gmail.id}") private String gmailId;
	@Value("${sendmail.gmail.password}") private String gmailPassword;
	
	public void sendMail(String email, String subject, String content) throws MessagingException {
		// Step1
		Properties mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
	 
		// Step2
		Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		MimeMessage generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		generateMailMessage.setSubject(subject);
		generateMailMessage.setContent(content, "text/html");
	 
		// Step3
		System.out.println("Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
	 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", gmailId, gmailPassword);
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}
