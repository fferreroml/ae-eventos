package com.aeeventos.core.webapps.action;

import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.exception.ConstraintViolationException;


import com.aeeventos.core.bean.Acreditacion;
import com.aeeventos.core.bean.Asistente;
import com.aeeventos.core.bean.Evento;
import com.aeeventos.core.bean.enums.EstadoAcreditacionEnum;
import com.aeeventos.core.bean.enums.EstadoEventoEnum;
import com.aeeventos.core.webapps.form.RegistroEventoForm;
import com.neosur.mailSender.bean.Email;
import com.neosur.mailSender.bean.MailHtmlTemplate;
import com.neosur.mailSender.exception.MailException;
import com.neosur.mailSender.send.IMailSender;
import com.neosur.mailSender.send.MailSender;

public class RegistroEventoAction extends DispatchAction {

	/**
	 * Metodo que permite inicializar el registro del evento, segun el evento
	 * que se le envia por request.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward onRegistroEvento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RegistroEventoForm registroEventoForm = (RegistroEventoForm) form;
		registroEventoForm.cleanForm();

		try {
			String eventoContexto = (request.getParameter("evento"));

			Evento evt = new Evento();
			evt.setContexto("/".concat(eventoContexto));
			evt = Evento.findByExample(evt).get(0);
			registroEventoForm.setEventoRegistro(evt);
			registroEventoForm.setId((evt.getIdEvento()));
			return mapping.findForward("registro");
		} catch (RuntimeException re) {
			setMessage(request, "public.hibernateError");
			return mapping.findForward("registro");
		} catch (Exception e) {
			setMessage(request, "public.generalError");
			return mapping.findForward("registro");
		}

	}

	/**
	 * Metodo para la persistencia de asistentes para un evento. En caso de que
	 * el asistente exista se le crea una nueva entrada en la tabla
	 * acreditacion, pero no se duplican los datos sino que se actualizan sus
	 * datos (teniendo en cuenta el campo dni como UNIQUE).
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward onGuardarRegistro(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RegistroEventoForm registroEventoForm = (RegistroEventoForm) form;

		try {
			int idEvento = (Integer.parseInt(request.getParameter("idEvento")));

			Evento evt = new Evento();
			evt = Evento.findById(idEvento);
			Acreditacion acred = new Acreditacion();
			acred.setEvento(evt);

			// Se setean los datos en un objeto asistente vacio.
			Asistente asistente = new Asistente();
			asistente.setNombre(registroEventoForm.getNombre());
			asistente.setApellido(registroEventoForm.getApellido());
			asistente.setCargo(registroEventoForm.getCargo());
			asistente.setCompania(registroEventoForm.getCompania());
			asistente.setTelefono(registroEventoForm.getTelefonoCompleto());
			asistente.setCelular(registroEventoForm.getCelularCompleto());
			asistente.setFax(registroEventoForm.getFaxCompleto());
			asistente.setEmail(registroEventoForm.getEmail());
			asistente.setDireccion(registroEventoForm.getDireccion());
			asistente.setCiudad(registroEventoForm.getCiudad());
			asistente.setDni(registroEventoForm.getDni());
			registroEventoForm.setId(idEvento);
			registroEventoForm.setContexto(evt.getContexto());
			registroEventoForm.setEventoRegistro(evt);

			if (evt.getEstadoEvento() != EstadoEventoEnum.PUBLICADO) {
				switch (evt.getEstadoEvento()) {
				case CERRADO :
					setMessage(request, "public.registro.cerrado");
					break;
				case FINALIZADO:
					setMessage(request, "public.registro.finalizado");
					break;
				case EN_CREACION:
					setMessage(request, "public.registro.creacion");
					break;

				default:
					setMessage(request, "public.registro.estadoInvalido");
					break;
				}
				return mapping.findForward("registro");
			}

			try {

				asistente.insert(null);

			} catch (ConstraintViolationException ce) {
				Asistente aux = new Asistente();

				// TODO ver q hacemos aca!
				aux = Asistente.findByDni(registroEventoForm.getDni());
				aux.setNombre(registroEventoForm.getNombre());
				aux.setApellido(registroEventoForm.getApellido());
				aux.setCargo(registroEventoForm.getCargo());
				aux.setCompania(registroEventoForm.getCompania());
				aux.setTelefono(registroEventoForm.getTelefonoCompleto());
				aux.setCelular(registroEventoForm.getCelularCompleto());
				aux.setFax(registroEventoForm.getFaxCompleto());
				aux.setEmail(registroEventoForm.getEmail());
				aux.setDireccion(registroEventoForm.getDireccion());
				aux.setCiudad(registroEventoForm.getCiudad());
				aux.setDni(registroEventoForm.getDni());
				aux.update(null);
				asistente = new Asistente();
				asistente = aux;
				log
						.debug("Asistente ya registrado en otro evento, se hizo un update de los datos");

			}

			// Seteo de la acreditacion
			Acreditacion acreditacion = new Acreditacion();
			acreditacion.setAsistente(asistente);
			acreditacion.setBorrado(false);
			acreditacion
					.setEstadoAcreditacion(EstadoAcreditacionEnum.PENDIENTE_ACREDITACION);
			acreditacion.setEvento(evt);
			acreditacion.setMailEnviado(false);
			acreditacion.setUrlImagen("");
			acreditacion.setDatamatrixGenerada(false);

			acreditacion.setAsistente(asistente);
			acreditacion.insert(null);

			sendMailRegistro(acreditacion, getContextoImages(request, evt),
					getContexto(request, evt), getContextoEvento(request, evt));

			// Insercion de la acreditacion.
			setMessage(request, "public.registroSuccess");
			return mapping.findForward("registro");
		} catch (RuntimeException re) {
			setMessage(request, "public.hibernateError");
			return mapping.findForward("registro");
		} catch (Exception e) {
			setMessage(request, "public.generalError");
			return mapping.findForward("registro");

		}

	}

	/**
	 * Metodo privado que permite el envio de mail a las personas registradas en
	 * el evento. Recibe como parametro el contexto de la aplicacion, el
	 * contexto del evento (teniendo en cuenta el campo en la base de datos) y
	 * el contexto donde se encuentran los datamatrix
	 * 
	 * @param acreditacion
	 * @param contextoImages
	 * @param contexto
	 * @param contextoEvento
	 * @throws MailException
	 */

	private void sendMailRegistro(Acreditacion acreditacion,
			String contextoImages, String contexto, String contextoEvento)
			throws MailException {

		Asistente asistente = acreditacion.getAsistente();
		ResourceBundle resourceMail = ResourceBundle.getBundle("mail");
		Email email = new Email();
		// Agregamos las direcciones de email correspondientes
		email.addRecipient(asistente.getEmail());
		email.setFrom("mail.smtp.from");
		// Se puede agregar destinatarios con copias

		// Texto a reamplzar en la personalización
		email.setTo(asistente.getNombreCompleto());

		MailHtmlTemplate template = new MailHtmlTemplate();
		template.getHtmlReplacements().put("$CONTEXT", contextoEvento);
		template.getHtmlReplacements().put("$ASISTENTE",
				asistente.getNombreCompleto());

		template.getHtmlReplacements().put("$EVENTO",
				acreditacion.getEvento().getNombre());

		// Datos estaticos del mail

		email.setSenderName(resourceMail.getString("mail.html.senderName"));
		email.setSentDate(new Date());
		email.setSubject(resourceMail.getString("mail.html.subject.solicitud"));

		// Url donde esta el html a enviar
		template.setUrlHtmlTemplate(contexto + "/"
				+ resourceMail.getString("mail.html.template.solicitud"));

		email.setTemplate(template);

		// Envio de mail propieamente dicho

//		try {
//			IMailSender sender = new MailSender();
//			sender.sendMail(email);
//		} catch (MailException e) {
//			throw e;
//		}

	}

	/**
	 * Obtenemos el contexto donde se encuentran los HTML de los templates de
	 * mails
	 * 
	 * @param request
	 * @param evento
	 * @return
	 */
	private String getContexto(HttpServletRequest request, Evento evento) {
		String contexto = request.getSession().getServletContext().getRealPath(
				"/template/");
		return contexto;

	}

	/**
	 * Obtenemos el contexto donde se encuentran ubicados los datamatrix
	 * 
	 * @param request
	 * @param evento
	 * @return
	 */

	private String getContextoImages(HttpServletRequest request, Evento evento) {
		/*
		 * Properties donde se encuentra la carpeta donde se guardan las
		 * imagenes para cada evento
		 */

		ResourceBundle resourceDatamatrix = ResourceBundle
				.getBundle("datamatrix");

		String contextoImagenes = ("http://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath()
				+ resourceDatamatrix.getString("evento.imagenes.ubicacion")
				+ evento.getContexto() + "/datamatrix");

		return contextoImagenes;

	}

	/**
	 * Obtenemos el contexto donde se encuentran las imagenes personalizadas del
	 * event para el registro.
	 * 
	 * @param request
	 * @param evento
	 * @return
	 */

	private String getContextoEvento(HttpServletRequest request, Evento evento) {
		ResourceBundle resourceDatamatrix = ResourceBundle
				.getBundle("datamatrix");

		/*
		 * Obtengo la unicacion de la imagen, obtenemos del properties la
		 * ubicacion donde se guardan todos los eventosy del
		 * evento.getContexto() el nombre de la carpeta para ese evento
		 */
		String contextoEvento = ("http://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath()
				+ resourceDatamatrix.getString("evento.imagenes.ubicacion") + evento
				.getContexto());

		return contextoEvento;
	}

	public void setMessage(HttpServletRequest request, String key) {
		ActionMessages msg = new ActionMessages();
		msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key));
		saveMessages(request, msg);
	}

}
