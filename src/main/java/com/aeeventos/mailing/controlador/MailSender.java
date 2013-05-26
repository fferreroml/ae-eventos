package com.aeeventos.mailing.controlador;

import java.io.StringWriter;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.aeeventos.mailing.config.MailingConfig;
import com.aeeventos.mailing.modelo.DatamatrixEmail;
import com.aeeventos.mailing.modelo.Email;

public class MailSender {

	private Logger logger = Logger.getLogger(MailSender.class);

	public void send(Email mail) throws Exception {
		
		logger.info("Entrando a metodo enviar email");
		try {
			
			logger.debug("Creando autenticador");
			Authenticator auth = new AuthSMTP();
			Session session = Session.getInstance(MailingConfig.getMailProperties(), auth);
			
			MimeMessage msg = new MimeMessage(session);
			msg.setSubject(mail.getSubject());
			msg.setFrom(new InternetAddress(MailingConfig.getEmailFrom(), MailingConfig.getEmailFromText()));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getRecipientsList()));
			
			logger.debug("Levantando template html");
			Multipart multipart = new MimeMultipart("related");
			BodyPart bodyPart = new MimeBodyPart();
			String html = getHtmlTemplate(mail);
			bodyPart.setContent(html,"text/html; charset=UTF-8");
			multipart.addBodyPart(bodyPart);

			if (mail instanceof DatamatrixEmail) {
				DatamatrixEmail datamatrixEmail = (DatamatrixEmail) mail;
				multipart.addBodyPart(getDatamatrixBodyPart(datamatrixEmail.getDatamatrixPath()));
			}
		    
			msg.setContent(multipart);
			
			logger.debug("Enviando email");
			Transport transport = session.getTransport();
			transport.connect();
			transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
			transport.close();
			logger.info("Email enviado correctamente a: " + mail.getRecipientsList());
			
		} catch (Exception ex) {
			logger.error("Error enviando email a: " + mail.getRecipientsList(), ex);
			throw new Exception(ex);
		} finally {
			logger.info("Saliendo del metodo enviar email");
		}
	}

	private String getHtmlTemplate(Email mail) {

		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loader", "class");
		velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		velocityEngine.setProperty("runtime.log.logsystem.log4j.category", "velocity");
		velocityEngine.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
		velocityEngine.init();

		VelocityContext context = new VelocityContext();
		Map<String, String> replacements = mail.getReplacements();
		replacements.put("contextweb", MailingConfig.getWebContext() + "/resources/images");
		for (String key : replacements.keySet()) {
			context.put(key, replacements.get(key));
		}
		
		Template template = velocityEngine.getTemplate(mail.getContextPath() + "/mailTemplates/" + mail.getTemplate());
		StringWriter output = new StringWriter();
		template.merge(context, output);

		return output.toString();
	}

	private BodyPart getDatamatrixBodyPart(String datamatrixPath) throws MessagingException {
		logger.debug("Agregando datamatrix al cuerpo del mail");
		BodyPart datamatrixBodyPart = new MimeBodyPart();
	    DataSource fds = new FileDataSource(datamatrixPath);
	    //TODO delete this line
		//DataSource fds = new URLDataSource(new URL("http://192.168.1.97:8080/ae-eventos/resources/images/logo_login.png"));
	    datamatrixBodyPart.setDataHandler(new DataHandler(fds));
	    datamatrixBodyPart.setHeader("Content-ID","<datamatrix>");
	    return datamatrixBodyPart;
	}

	final class AuthSMTP extends javax.mail.Authenticator {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(MailingConfig.getEmailAccountUserName(), MailingConfig.getEmailAccountUserPass());
		}
	}
	
	public static void main(String args[]) {
		
		MailSender sender = new MailSender();
		
		DatamatrixEmail email = new DatamatrixEmail();
		email.setSubject("Prueba de AE Eventos");
		email.addRecipient("turcogino@hotmail.com");
		//email.addRecipient("ocnarf.franco@gmail.com");
		//email.addRecipient("villarruelemanuel@gmail.com");
		email.addReplacement("nombre", "emanuel");
		email.setDatamatrixPath("/home/gturco/datamatrix/franco.png");
		
		try {
			sender.send(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
