package com.aeeventos.mailing.modelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Email {

	private String subject;
	private Set<String> recipients;
	private Map<String, String> replacements;
	private String template;
	private String contextPath;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Set<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<String> recipients) {
		this.recipients = recipients;
	}

	public void addRecipient(String recipient) {
		if (this.recipients == null) {
			this.recipients = new HashSet<String>();
		}
		this.recipients.add(recipient);
	}

	public String getRecipientsList() {
		StringBuilder recipientsList = new StringBuilder();
		for (String recipient : this.recipients) {
			recipientsList.append(recipient).append(",");
		}
		return recipientsList.toString().substring(0,
				recipientsList.length() - 1);
	}

	public Map<String, String> getReplacements() {
		if (replacements == null) {
			replacements = new HashMap<String, String>();
		}
		return replacements;
	}

	public void setReplacements(Map<String, String> replacements) {
		this.replacements = replacements;
	}

	public void addReplacement(String key, String value) {
		if (this.replacements == null) {
			this.replacements = new HashMap<String, String>();
		}
		this.replacements.put(key, value);
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
}
