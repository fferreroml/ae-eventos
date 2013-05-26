package com.aeeventos.core.thread;

import java.awt.Color;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;



import com.aeeventos.core.bean.Acreditacion;
import com.aeeventos.core.bean.enums.EstadoAcreditacionEnum;
import com.neosur.datamatrix.bean.Datamatrix;
import com.neosur.datamatrix.bean.EncodingMode;
import com.neosur.datamatrix.bean.ImageType;
import com.neosur.datamatrix.bean.PreferredFormat;
import com.neosur.datamatrix.exception.DatamatrixException;
import com.neosur.datamatrix.generation.DatamatrixGenerationJava4Less;
import com.neosur.mailSender.bean.Email;
import com.neosur.mailSender.bean.MailHtmlTemplate;
import com.neosur.mailSender.exception.MailException;
import com.neosur.mailSender.send.IMailSender;
import com.neosur.mailSender.send.MailSender;


public class ThreadNotifications implements Runnable {

	private final static Logger log = Logger
			.getLogger(ThreadNotifications.class);

	private List<Acreditacion> listaAcreditado;
	private String contexto;
	private String contextoEvento;
	private String contextoImagenes;

	/**
	 * 
	 * @param listaAcreditado
	 * @param contexto del evento
	 * 
	 */

	public ThreadNotifications(List<Acreditacion> listaAcreditado,
			String contexto,String contextoImagenes, String contextoEvento) {

		this.listaAcreditado = listaAcreditado;
		this.contexto = contexto;
		this.contextoEvento = contextoEvento;
		this.contextoImagenes = contextoImagenes;

	}



	public void run() {

		for (Acreditacion acreditacion : listaAcreditado) {
			
			
			/*
			 * Si no tiene datamatrix generada la genera
			 */
			
			if(!acreditacion.isDatamatrixGenerada() && acreditacion.getEstadoAcreditacion()==EstadoAcreditacionEnum.ACREDITADO)
			{
				
				generarDataMatrix(acreditacion, contextoEvento);
			}
			/*
			 * Para que le envie las notificaciones debe tener una datamatrix
			 * generada y no tener el mail enviado.
			 */
			if (!acreditacion.isMailEnviado()
					&& acreditacion.isDatamatrixGenerada() && acreditacion.getEstadoAcreditacion()==EstadoAcreditacionEnum.ACREDITADO) {
				
					try {
						sendMailAcreditacion(acreditacion, contextoImagenes);

					} catch (MailException e) {
						log.error("Error en el envio de mail");
						e.printStackTrace();
					} catch (RuntimeException e) {
						log.error("Error de hibernate");
						e.printStackTrace();
					}
				
			}

		}

	}

	/*
	 * metodo privado que hace el envio de los e-mails
	 */
	private void sendMailAcreditacion(Acreditacion acreditacion,  String contextoImages)
			throws MailException, RuntimeException {

		ResourceBundle resourceMail = ResourceBundle.getBundle("mail");

		Email email = new Email();

		email.setFrom(resourceMail.getString("mail.smtp.from"));
		// Agregamos las direcciones de email correspondientes

		email.addRecipient(acreditacion.getAsistente().getEmail());

		// Texto a reamplzar en la personalización
		email.setTo(acreditacion.getAsistente().getNombreCompleto());

		MailHtmlTemplate template = new MailHtmlTemplate();
		template.getHtmlReplacements().put("$CONTEXT",
				contextoImages);
		
		template.getHtmlReplacements().put("$EVENTO",
				acreditacion.getEvento().getNombre());
		
		
		template.getHtmlReplacements().put("$ASISTENTE",
				acreditacion.getAsistente().getNombreCompleto());
		template.getHtmlReplacements().put(
				"$DATAMATRIX",
				contextoImages+"/datamatrix/" + acreditacion.getAsistente().getDni()
						+ ".jpg");

		// Datos estaticos del mail
		email.setSenderName(resourceMail.getString("mail.html.senderName"));
		email.setSentDate(Calendar.getInstance().getTime());
		email.setSubject(resourceMail
				.getString("mail.html.subject.acreditacion"));
		email.setMessage("");

		// Url donde esta el html a enviar
		template.setUrlHtmlTemplate(contexto + "/"
				+ resourceMail.getString("mail.html.template.acreditacion"));
		email.setTemplate(template);
		// Envio de mail propieamente dicho

		try {
			IMailSender sender = new MailSender();
			//sender.sendMail(email);
			Acreditacion.mailEnviado(acreditacion);

//		} catch (MailException e) {
//			log.error("Mail Exception", e);
		} catch (RuntimeException e) {
			log.error("Hibernate error", e);
			
		}

	}
	
	private void generarDataMatrix(Acreditacion acreditacion, String contextoEvento) {
		
		DatamatrixGenerationJava4Less dMatrixGeneration = new DatamatrixGenerationJava4Less();
		Datamatrix dataMatrix = new Datamatrix();

		ResourceBundle resourceDatamatrix = ResourceBundle
				.getBundle("datamatrix");
		dataMatrix.setBackground(Color.white);
		dataMatrix.setHeight(200);
		dataMatrix.setWidth(200);
		dataMatrix.setForeground(Color.black);
		dataMatrix.setImageType(ImageType.JPG);
		dataMatrix.setImagePath(contextoEvento);
		dataMatrix.setPreferredFormat(PreferredFormat.C18x18);
		dataMatrix.setEncodingMode(EncodingMode.ASCII);
		dataMatrix.setProccessTilde(false);
		dataMatrix.setMargin(18);
		dataMatrix.setAccessImage(false);
		dataMatrix.setAccessImage(Boolean.parseBoolean(resourceDatamatrix
				.getString("datamatrix.isAccessImage")));

		if (dataMatrix.isAccessImage()) {
			dataMatrix.setAccessImagePath(resourceDatamatrix
					.getString("datamatrix.AccessImagepath"));
			dataMatrix.setCentrar(true);
		}

		try {
			
			/*
			 * Aqui estamos generando datamatrix a partir del DNI.
			 */

			dataMatrix.setDataToEncode(acreditacion.getAsistente().getDni());
			dataMatrix.setName("/" + acreditacion.getAsistente().getDni());
			
			/*
			 * Aca estamos indicando el nombre de la imagen datamatrix generada.
			 */

			acreditacion.setUrlImagen(acreditacion.getAsistente().getDni());
			dMatrixGeneration.generateDatamatrix(dataMatrix);
			Acreditacion.datamatrixGenerada(acreditacion);

		} catch (DatamatrixException e) {

			log.error("Error al generar datamatrix para el asistente: "
					+ acreditacion.getAsistente().getNombreCompleto(), e);
			e.printStackTrace();

		} catch (RuntimeException e) {
			log.error("Error al guardar el datamatrix en la base de datos: "
					+ acreditacion.getAsistente().getNombreCompleto(), e);
			e.printStackTrace();
		}

	}
}
