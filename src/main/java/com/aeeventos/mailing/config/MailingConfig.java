package com.aeeventos.mailing.config;

import java.util.Properties;
import java.util.ResourceBundle;

public class MailingConfig {

	private static ResourceBundle config = ResourceBundle.getBundle("mailing-config");

	public static String getEmailAccountUserName() {
		return config.getString("message.mail.username");
	}

	public static String getEmailAccountUserPass() {
		return config.getString("message.mail.userpass");
	}

	public static String getEmailSmtpAuth() {
		return config.getString("message.mail.smtp.auth");
	}

	public static String getEmailSmtpStartTls() {
		return config.getString("message.mail.smtp.starttls");
	}

	public static String getEmailSmtpHost() {
		return config.getString("message.mail.smtp.host");
	}

	public static String getEmailSmtpPort() {
		return config.getString("message.mail.smtp.port");
	}

	public static String getEmailFrom() {
		return config.getString("message.mail.from");
	}

	public static String getEmailFromText() {
		return config.getString("message.mail.from.text");
	}
	
	public static boolean isSSLEnable() {
		return (config.getString("message.mail.ssl.enable").equalsIgnoreCase("true"));
	}
	
	public static String getWebContext() {
		return config.getString("message.mail.web.context");
	}
	
	public static Properties getMailProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.user", MailingConfig.getEmailAccountUserName());
		properties.put("mail.smtp.host", MailingConfig.getEmailSmtpHost());
		properties.put("mail.smtp.port", MailingConfig.getEmailSmtpPort());
		properties.put("mail.smtp.auth", MailingConfig.getEmailSmtpAuth());
		properties.put("mail.smtp.starttls.enable", MailingConfig.getEmailSmtpStartTls());
		properties.put("mail.transport.protocol", "smtp");
		if (MailingConfig.isSSLEnable()) {
			properties.put("mail.smtp.socketFactory.port", MailingConfig.getEmailSmtpPort());
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
		}
		return properties;
	}
	
}
