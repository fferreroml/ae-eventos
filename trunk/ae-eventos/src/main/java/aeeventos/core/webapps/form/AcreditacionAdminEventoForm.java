package aeeventos.core.webapps.form;

import org.apache.struts.action.ActionForm;
import java.util.List;

import aeeventos.core.bean.Acreditacion;
import aeeventos.core.bean.Evento;
import aeeventos.core.bean.enums.EstadoAcreditacionEnum;


public class AcreditacionAdminEventoForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Evento> eventos;
	private int eventoSeleccionado;
	private String apellidoFiltro;
	private String nombreFiltro;
	private String emailFiltro;
	private String estadoFiltro;
	private List<EstadoAcreditacionEnum> estados;
	private List<Acreditacion> acreditaciones;
	
	/**
	 * @return the acreditaciones
	 */
	public List<Acreditacion> getAcreditaciones() {
		return acreditaciones;
	}

	/**
	 * @param acreditaciones the acreditaciones to set
	 */
	public void setAcreditaciones(List<Acreditacion> acreditaciones) {
		this.acreditaciones = acreditaciones;
	}

	/**
	 * @return the apellidoFiltro
	 */
	public String getApellidoFiltro() {
		return apellidoFiltro;
	}

	/**
	 * @param apellidoFiltro the apellidoFiltro to set
	 */
	public void setApellidoFiltro(String apellidoFiltro) {
		this.apellidoFiltro = apellidoFiltro;
	}

	/**
	 * @return the nombreFiltro
	 */
	public String getNombreFiltro() {
		return nombreFiltro;
	}

	/**
	 * @param nombreFiltro the nombreFiltro to set
	 */
	public void setNombreFiltro(String nombreFiltro) {
		this.nombreFiltro = nombreFiltro;
	}

	/**
	 * @return the emailFiltro
	 */
	public String getEmailFiltro() {
		return emailFiltro;
	}

	/**
	 * @param emailFiltro the emailFiltro to set
	 */
	public void setEmailFiltro(String emailFiltro) {
		this.emailFiltro = emailFiltro;
	}



	/**
	 * @return the eventos
	 */
	public List<Evento> getEventos() {
		return eventos;
	}

	/**
	 * @return the estadoFiltro
	 */
	public String getEstadoFiltro() {
		return estadoFiltro;
	}

	/**
	 * @param estadoFiltro the estadoFiltro to set
	 */
	public void setEstadoFiltro(String estadoFiltro) {
		this.estadoFiltro = estadoFiltro;
	}

	/**
	 * @return the estados
	 */
	public List<EstadoAcreditacionEnum> getEstados() {
		return estados;
	}

	/**
	 * @param estados the estados to set
	 */
	public void setEstados(List<EstadoAcreditacionEnum> estados) {
		this.estados = estados;
	}

	/**
	 * @param eventos the eventos to set
	 */
	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	
	/**
	 * @return the eventoSeleccionado
	 */
	public int getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	/**
	 * @param eventoSeleccionado the eventoSeleccionado to set
	 */
	public void setEventoSeleccionado(int eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}



	public void cleanForm()
	{
		this.eventos = null;
		this.estadoFiltro = null;
		this.estados = null;
		this.emailFiltro = "";
		this.nombreFiltro = "";
		this.apellidoFiltro = "";
		this.acreditaciones = null;
		this.eventoSeleccionado= 0;
	}




}
