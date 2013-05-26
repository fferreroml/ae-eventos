package com.aeeventos.core.webapps.form;

import org.apache.struts.action.ActionForm;

import com.aeeventos.core.bean.Asistente;
import com.aeeventos.core.bean.Evento;




public class ControlAccesoForm  extends ActionForm{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String evento;
	private String mail;
	private Asistente asistente;
	private boolean acceso;
	private int id; // id del evento
	private Evento eventoControl;
	private String dni;
	private String nombre; // nombre del evento
	private String urlImagen;
	
	public void cleanForm()
	{
		this.mail = "";
		this.asistente = null;
		this.acceso = false;
		this.evento = null;
		this.id = 0;
		this.eventoControl = null;
		this.dni = "";
		this.nombre = "";
		this.urlImagen = "";
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}

	public void setAsistente(Asistente asistente) {
		this.asistente = asistente;
	}

	public Asistente getAsistente() {
		return asistente;
	}


	public void setAcceso(boolean acceso) {
		this.acceso = acceso;
	}

	public boolean getAcceso() {
		return acceso;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getEvento() {
		return evento;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setEventoControl(Evento eventoControl) {
		this.eventoControl = eventoControl;
	}

	public Evento getEventoControl() {
		return eventoControl;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDni() {
		return dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

}
