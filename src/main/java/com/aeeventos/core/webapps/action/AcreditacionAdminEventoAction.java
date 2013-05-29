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
import com.aeeventos.core.webapps.form.AcreditacionAdminEventoForm;
import com.aeeventos.util.AcreditacionesMailGenerator;

public class AcreditacionAdminEventoAction extends SecuredDispatchAction {

	

	/**
	 * Metodo que lista todos los eventos para el cual el registrado es
	 * administrador de el mismo.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward onListarEventos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute(
				"AcreditacionesUser");
		AcreditacionAdminEventoForm acreditacionesForm = (AcreditacionAdminEventoForm) form;
		acreditacionesForm.cleanForm();

		List<Evento> eventos;
		try {
			Evento e = new Evento();
			e.setUsuario(usuarioLogueado);
			eventos = Evento.findByExampleUsuario(e);
			acreditacionesForm.setEventos(eventos);
			return mapping.findForward("listadoEventos");
		} catch (RuntimeException re) {
			return mapping.findForward("hibernateError");
		}

	}

	/**
	 * Metodo que permite ingresar el detalle de un evento, para filtrar segun
	 * distintos campos rechazar o acreditar una acreditacion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward onListarDetalleEvento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AcreditacionAdminEventoForm acreditacionesForm = (AcreditacionAdminEventoForm) form;
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

		Asistente asistente = new Asistente();
		asistente.setApellido(acreditacionesForm.getApellidoFiltro());
		asistente.setNombre(acreditacionesForm.getNombreFiltro());
		asistente.setEmail(acreditacionesForm.getEmailFiltro());
		filter.setAsistente(asistente);
		filter.setEvento(evento);

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
		}

	}

	public ActionForward onCambiarEstado(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AcreditacionAdminEventoForm acreditacionesForm = (AcreditacionAdminEventoForm) form;
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

		AcreditacionAdminEventoForm acreditacionesForm = (AcreditacionAdminEventoForm) form;
		acreditacionesForm.cleanForm();
		String contexto = request.getSession().getServletContext()
				.getRealPath("");

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
		// int idEvento = Integer.parseInt(request.getParameter("evento"));
		int idAcreditacion = Integer.parseInt(request
				.getParameter("acreditacion"));

		
		Acreditacion acreditacion = new Acreditacion();
		String contexto = request.getSession().getServletContext().getRealPath("");

		try {
			acreditacion = Acreditacion.findById(idAcreditacion);
		} catch (RuntimeException e1) {

			e1.printStackTrace();
			return mapping.findForward("hibernateError");
		} catch (Exception e) {
			return mapping.findForward("generalError");
		}
		
		boolean emailSentOK = AcreditacionesMailGenerator.mailGenerator(acreditacion, contexto);

		if(emailSentOK){
			return mapping.findForward("envioEmailOK");
		}
		else {
			return mapping.findForward("mailError");
		}	
	}

	public ActionForward onCambiarEstadoEvento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute(
				"AcreditacionesUser");
		AcreditacionAdminEventoForm acreditacionesForm = (AcreditacionAdminEventoForm) form;
		acreditacionesForm.cleanForm();

		int idEvento = Integer.parseInt(request.getParameter("evento"));
		int estadoEvento = Integer.parseInt(request.getParameter("estado"));

		List<Evento> eventos;
		try {

			Evento evt = Evento.findById(idEvento);
			evt.setEstadoEvento(EstadoEventoEnum.values()[estadoEvento]);
			Evento.update(evt);
			evt = new Evento();
			evt.setUsuario(usuarioLogueado);
			eventos = Evento.findByExampleUsuario(evt);
			acreditacionesForm.setEventos(eventos);
			return mapping.findForward("listadoEventos");
		} catch (RuntimeException re) {
			return mapping.findForward("hibernateError");
		}
	}
}
