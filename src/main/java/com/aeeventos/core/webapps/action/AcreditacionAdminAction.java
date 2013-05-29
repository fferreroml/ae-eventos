package com.aeeventos.core.webapps.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aeeventos.core.bean.Acreditacion;
import com.aeeventos.core.bean.Asistente;
import com.aeeventos.core.bean.Evento;
import com.aeeventos.core.bean.Usuario;
import com.aeeventos.core.bean.enums.EstadoAcreditacionEnum;
import com.aeeventos.core.bean.enums.EstadoEventoEnum;
import com.aeeventos.core.thread.ThreadNotifications;
import com.aeeventos.core.webapps.action.security.SecuredDispatchAction;
import com.aeeventos.core.webapps.form.AcreditacionAdminForm;
import com.aeeventos.util.AcreditacionesMailGenerator;

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
			filter.setEstadoAcreditacion(EstadoAcreditacionEnum.values()[Integer
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
				acreditacion.cambiarEstado(
						EstadoAcreditacionEnum.ACREDITADO,
						(Usuario) request.getSession().getAttribute(
								"AcreditacionesUser"));
			} else {
				acreditacion.cambiarEstado(
						EstadoAcreditacionEnum.RECHAZADA,
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

		String contexto = request.getSession().getServletContext()
				.getRealPath("");

		if (evento.getEstadoEvento().getValor() == EstadoEventoEnum.CERRADO
				.getValor()
				|| evento.getEstadoEvento().getValor() == EstadoEventoEnum.PUBLICADO
						.getValor()) {
			Thread hilo = new Thread(new ThreadNotifications(listaAcreditado,
					contexto));
			hilo.start();

			return mapping.findForward("envioEmailOK");
		} else {
			return mapping.findForward("envioEmailInvalido");
		}

	}

	public ActionForward onEnviarMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//int idEvento = Integer.parseInt(request.getParameter("evento"));
		int idAcreditacion = Integer.parseInt(request
				.getParameter("acreditacion"));

		String contexto = request.getSession().getServletContext()
				.getRealPath("");
		Acreditacion acreditacion = new Acreditacion();

		try {
			acreditacion = Acreditacion.findById(idAcreditacion);
		} catch (RuntimeException e1) {

			e1.printStackTrace();
			return mapping.findForward("hibernateError");
		} catch (Exception e) {
			return mapping.findForward("generalError");
		}

		boolean emailSentOK = AcreditacionesMailGenerator.mailGenerator(
				acreditacion, contexto);

		if (emailSentOK) {
			return mapping.findForward("envioEmailOK");
		} else {
			return mapping.findForward("mailError");
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
