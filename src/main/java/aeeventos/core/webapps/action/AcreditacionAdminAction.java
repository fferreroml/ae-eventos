package aeeventos.core.webapps.action;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import aeeventos.core.bean.Acreditacion;
import aeeventos.core.bean.Asistente;
import aeeventos.core.bean.Evento;
import aeeventos.core.bean.Usuario;
import aeeventos.core.bean.enums.EstadoAcreditacionEnum;
import aeeventos.core.bean.enums.EstadoEventoEnum;
import aeeventos.core.thread.ThreadNotifications;
import aeeventos.core.webapps.action.security.SecuredDispatchAction;
import aeeventos.core.webapps.form.AcreditacionAdminForm;

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

public class AcreditacionAdminAction extends SecuredDispatchAction {

	public ActionForward onListar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		AcreditacionAdminForm acreditacionesForm = (AcreditacionAdminForm) form;
		acreditacionesForm.cleanForm();

		List<Evento> eventos;
		try {
			eventos = Evento.getAll();
			acreditacionesForm.setEventos(eventos);
			return mapping.findForward("listadoEventos");
		} catch (RuntimeException re) {
			return mapping.findForward("hibernateError");
		}

	}

	public ActionForward onListarDetalleEvento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AcreditacionAdminForm acreditacionesForm = (AcreditacionAdminForm) form;
		int eventoSeleccionado;
		Evento evento = new Evento();

		if (acreditacionesForm.getEventoSeleccionado() != 0) {
			eventoSeleccionado = acreditacionesForm.getEventoSeleccionado();
		} else {
			eventoSeleccionado = Integer.parseInt(request
					.getParameter("evento"));
			acreditacionesForm.setEventoSeleccionado(eventoSeleccionado);
		}

		evento = Evento.findById(eventoSeleccionado);

		Acreditacion filter = new Acreditacion();
		filter.setEvento(evento);
		Asistente asistente = new Asistente();
		asistente.setApellido(acreditacionesForm.getApellidoFiltro());
		asistente.setNombre(acreditacionesForm.getNombreFiltro());
		asistente.setEmail(acreditacionesForm.getEmailFiltro());
		filter.setAsistente(asistente);

		if (acreditacionesForm.getEstadoFiltro() != null
				&& !acreditacionesForm.getEstadoFiltro().equals("-1")) {
			filter
					.setEstadoAcreditacion(EstadoAcreditacionEnum.values()[Integer
							.parseInt(acreditacionesForm.getEstadoFiltro())]);
		} else {
			filter.setEstadoAcreditacion(null);
		}

		acreditacionesForm.setEstados(EstadoAcreditacionEnum.getAll());
		try {

			List<Acreditacion> listaAcreditados = Acreditacion
					.findByExample(filter);
			acreditacionesForm.setAcreditaciones(listaAcreditados);
			return mapping.findForward("listaDetalleEvento");
		} catch (RuntimeException e) {
			return mapping.findForward("hibernateError");
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("generalError");
		}

	}

	public ActionForward onCambiarEstado(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AcreditacionAdminForm acreditacionesForm = (AcreditacionAdminForm) form;
		acreditacionesForm.cleanForm();

		int idAcreditacion = Integer.parseInt(request
				.getParameter("acreditacion"));
		String estado = (request.getParameter("estado"));
		try {
			Acreditacion acreditacion = Acreditacion.findById(idAcreditacion);
			if (estado.equalsIgnoreCase("aprobar")) {
				acreditacion.cambiarEstado(EstadoAcreditacionEnum.ACREDITADO,
						(Usuario) request.getSession().getAttribute(
								"AcreditacionesUser"));
			} else {
				acreditacion.cambiarEstado(EstadoAcreditacionEnum.RECHAZADA,
						(Usuario) request.getSession().getAttribute(
								"AcreditacionesUser"));

			}
			return onListarDetalleEvento(mapping, acreditacionesForm, request,
					response);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return mapping.findForward("hibernateError");
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("generalError");
		}

	}

	/*
	 * Este metodo envia notificaciones mediante e-mails y sms una vez que los
	 * asistentes tienen generados datamatrix y estan en estado acreditados.
	 * LLama a un hilo que se encarga de enviar las notificaciones.
	 */

	public ActionForward onEnviarMails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		AcreditacionAdminForm acreditacionesForm = (AcreditacionAdminForm) form;
		acreditacionesForm.cleanForm();

		List<Acreditacion> listaAcreditado;
		int idEvento = Integer.parseInt(request.getParameter("evento"));
		Acreditacion filter = new Acreditacion();

		Evento evento = new Evento();
		evento.setIdEvento(idEvento);

		try {
			evento = Evento.findById(idEvento);
			filter.setEvento(evento);
			listaAcreditado = Acreditacion.findByExampleEnviarMails(filter);

		} catch (RuntimeException e1) {

			e1.printStackTrace();
			return mapping.findForward("hibernateError");
		} catch (Exception e) {
			return mapping.findForward("generalError");
		}

		String contexto = request.getSession().getServletContext().getRealPath(
				"/template/");

		/*
		 * Properties donde se encuentra la carpeta donde se guardan las
		 * imagenes para cada evento
		 */

		ResourceBundle resourceDatamatrix = ResourceBundle
				.getBundle("datamatrix");

		String contextoImagenes = ("http://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath()
				+ resourceDatamatrix.getString("evento.imagenes.ubicacion") + evento
				.getContexto());

		/*
		 * Obtengo la unicacion de la imagen, obtenemos del properties la
		 * ubicacion donde se guardan todos los eventosy del
		 * evento.getContexto() el nombre de la carpeta para ese evento
		 */
		String contextoEvento = request.getSession().getServletContext()
				.getRealPath(
						resourceDatamatrix
								.getString("evento.imagenes.ubicacion")
								+ evento.getContexto() + "/datamatrix/");
		/*
		 * creamos el hilo de ejecucion de las notificaciones parametros: la
		 * lista de los acreditados, string contexto
		 */

		
		if(evento.getEstadoEvento().getValor()==EstadoEventoEnum.CERRADO.getValor() ||
				evento.getEstadoEvento().getValor()==EstadoEventoEnum.PUBLICADO.getValor())
		{
			Thread hilo = new Thread(new ThreadNotifications(listaAcreditado,
					contexto, contextoImagenes, contextoEvento));
			hilo.start();

			return mapping.findForward("envioEmailOK");
		}
		else
		{
			return mapping.findForward("envioEmailInvalido");
		}

	}

	public ActionForward onEnviarMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int idEvento = Integer.parseInt(request.getParameter("evento"));
		int idAcreditacion = Integer.parseInt(request
				.getParameter("acreditacion"));

		Evento evt = new Evento();
		Acreditacion acred = new Acreditacion();

		try {
			evt = Evento.findById(idEvento);
			acred = Acreditacion.findById(idAcreditacion);
		} catch (RuntimeException e1) {

			e1.printStackTrace();
			return mapping.findForward("hibernateError");
		} catch (Exception e) {
			return mapping.findForward("generalError");
		}

		try {
			sendMail(acred, getContextoImages(request, evt), getContexto(
					request, evt), getContextoEvento(request, evt));
		}

		catch (MailException e) {

			return mapping.findForward("mailError");
		} catch (DatamatrixException e) {
			return mapping.findForward("generalError");
		}

		return mapping.findForward("envioEmailOK");
	}

	private void sendMail(Acreditacion acreditacion, String contextoImages,
			String contexto, String contextoEvento) throws MailException,
			DatamatrixException {

		Asistente asistente = acreditacion.getAsistente();
		ResourceBundle resourceMail = ResourceBundle.getBundle("mail");

		try {
			if (!acreditacion.isDatamatrixGenerada())
				generarDataMatrix(acreditacion, contextoImages);
		} catch (DatamatrixException de) {
			throw de;
		}

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

		template.getHtmlReplacements().put(
				"$DATAMATRIX",
				contextoEvento + "/datamatrix/"
						+ acreditacion.getAsistente().getDni() + ".jpg");

		// Datos estaticos del mail

		email.setSenderName(resourceMail.getString("mail.html.senderName"));
		email.setSentDate(new Date());
		email.setSubject(resourceMail
				.getString("mail.html.subject.acreditacion"));

		// Url donde esta el html a enviar
		template.setUrlHtmlTemplate(contexto + "/"
				+ resourceMail.getString("mail.html.template.acreditacion"));

		email.setTemplate(template);

		// Envio de mail propieamente dicho

		try {
			IMailSender sender = new MailSender();
			sender.sendMail(email);
			Acreditacion.mailEnviado(acreditacion);
		} catch (MailException e) {
			throw e;
		}

	}

	private String getContexto(HttpServletRequest request, Evento evento) {
		String contexto = request.getSession().getServletContext().getRealPath(
				"/template/");
		return contexto;

	}

	private String getContextoImages(HttpServletRequest request, Evento evento) {

		ResourceBundle resourceDatamatrix = ResourceBundle
				.getBundle("datamatrix");

		String contextoImagenes = request.getSession().getServletContext()
				.getRealPath(
						resourceDatamatrix
								.getString("evento.imagenes.ubicacion")
								+ evento.getContexto() + "/datamatrix/");

		return contextoImagenes;

	}

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

	private void generarDataMatrix(Acreditacion acreditacion,
			String contextoEvento) throws DatamatrixException, RuntimeException {

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

			dataMatrix.setDataToEncode(acreditacion.getAsistente().getDni());
			dataMatrix.setName("/" + acreditacion.getAsistente().getDni());

			acreditacion.setUrlImagen(acreditacion.getAsistente().getDni()
					+ "." + dataMatrix.getImageType().getExtencion());
			dMatrixGeneration.generateDatamatrix(dataMatrix);
			Acreditacion.datamatrixGenerada(acreditacion);

		} catch (DatamatrixException e) {

			throw new DatamatrixException(
					"Error al generar datamatrix para el asistente: "
							+ acreditacion.getAsistente().getNombreCompleto(),
					e);

		} catch (RuntimeException e) {
			throw e;
		}

	}

	public ActionForward onCambiarEstadoEvento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AcreditacionAdminForm acreditacionesForm = (AcreditacionAdminForm) form;
		acreditacionesForm.cleanForm();

		int idEvento = Integer.parseInt(request.getParameter("evento"));
		int estadoEvento = Integer.parseInt(request.getParameter("estado"));

		List<Evento> eventos;
		try {

			Evento evt = Evento.findById(idEvento);
			evt.setEstadoEvento(EstadoEventoEnum.values()[estadoEvento]);
			Evento.update(evt);
			eventos = Evento.getAll();
			acreditacionesForm.setEventos(eventos);
			return mapping.findForward("listadoEventos");
		} catch (RuntimeException re) {
			return mapping.findForward("hibernateError");
		}
	}

}
