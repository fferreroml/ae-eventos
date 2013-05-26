package com.aeeventos.core.webapps.form;

import org.apache.struts.action.ActionForm;

import com.aeeventos.core.bean.Evento;


public class RegistroEventoForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String evento;
	private int idEvento;
	private String dni;
	private String nombre;
	private String apellido;
	private String cargo;
	private String contexto;
	private String compania;
	private String telPaisCod;
	private String telAreaCod;
	private String telefono;
	private String celPaisCod;
	private String celAreaCod;
	private String celular;
	private String faxPaisCod;
	private String faxAreaCod;
	private String fax;
	private String email;
	private String emailConfirm;
	private String direccion;
	private String ciudad;
	private String referencia;
	private String idAcreditacion;
	private Evento eventoRegistro;
	private String method;
	private String mens;

	
	public void cleanForm()
	{
		this.evento = "";
		this.setEventoRegistro(null);
		this.idAcreditacion = "";
		this.nombre = "";
		this.apellido = "";
		this.cargo = "";
		this.compania = "";
		this.telefono = "";
		this.celular = "";
		this.fax = "";
		this.email = "";
		this.direccion = "";
		this.ciudad = "";
		this.referencia = "";
		this.telAreaCod = "";
		this.telPaisCod = "";
		this.celAreaCod = "";
		this.celPaisCod = "";
		this.faxAreaCod = "";
		this.faxPaisCod = "";
		this.emailConfirm = "";
		this.dni = "";
		this.mens = "";

	}


	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}


	/**
	 * @param evento the evento to set
	 */
	public void setEvento(String evento) {
		this.evento = evento;
	}


	/**
	 * @return the id
	 */
	public int getIdEvento() {
		return idEvento;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int idEvento) {
		this.idEvento = idEvento;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the compania
	 */
	public String getCompania() {
		return compania;
	}

	/**
	 * @param compania the compania to set
	 */
	public void setCompania(String compania) {
		this.compania = compania;
	}

	/**
	 * @return the telPaisCod
	 */
	public String getTelPaisCod() {
		return telPaisCod;
	}

	/**
	 * @param telPaisCod the telPaisCod to set
	 */
	public void setTelPaisCod(String telPaisCod) {
		this.telPaisCod = telPaisCod;
	}

	/**
	 * @return the telAreaCod
	 */
	public String getTelAreaCod() {
		return telAreaCod;
	}

	/**
	 * @param telAreaCod the telAreaCod to set
	 */
	public void setTelAreaCod(String telAreaCod) {
		this.telAreaCod = telAreaCod;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the celPaisCod
	 */
	public String getCelPaisCod() {
		return celPaisCod;
	}

	/**
	 * @param celPaisCod the celPaisCod to set
	 */
	public void setCelPaisCod(String celPaisCod) {
		this.celPaisCod = celPaisCod;
	}

	/**
	 * @return the celAreaCod
	 */
	public String getCelAreaCod() {
		return celAreaCod;
	}

	/**
	 * @param celAreaCod the celAreaCod to set
	 */
	public void setCelAreaCod(String celAreaCod) {
		this.celAreaCod = celAreaCod;
	}

	/**
	 * @return the celular
	 */
	public String getCelular() {
		return celular;
	}

	/**
	 * @param celular the celular to set
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * @return the faxPaisCod
	 */
	public String getFaxPaisCod() {
		return faxPaisCod;
	}

	/**
	 * @param faxPaisCod the faxPaisCod to set
	 */
	public void setFaxPaisCod(String faxPaisCod) {
		this.faxPaisCod = faxPaisCod;
	}

	/**
	 * @return the faxAreaCod
	 */
	public String getFaxAreaCod() {
		return faxAreaCod;
	}

	/**
	 * @param faxAreaCod the faxAreaCod to set
	 */
	public void setFaxAreaCod(String faxAreaCod) {
		this.faxAreaCod = faxAreaCod;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emailConfirm
	 */
	public String getEmailConfirm() {
		return emailConfirm;
	}

	/**
	 * @param emailConfirm the emailConfirm to set
	 */
	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	/**
	 * @return the idAcreditacion
	 */
	public String getIdAcreditacion() {
		return idAcreditacion;
	}

	/**
	 * @param idAcreditacion the idAcreditacion to set
	 */
	public void setIdAcreditacion(String idAcreditacion) {
		this.idAcreditacion = idAcreditacion;
	}
	/**
	 * @return the contexto
	 */
	public String getContexto() {
		return contexto;
	}

	/**
	 * @param contexto the contexto to set
	 */
	public void setContexto(String contexto) {
		this.contexto = contexto;
	}


	public void setEventoRegistro(Evento eventoRegistro) {
		this.eventoRegistro = eventoRegistro;
	}


	public Evento getEventoRegistro() {
		return eventoRegistro;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getMethod() {
		return method;
	}
	
	public String getTelefonoCompleto(){
		return this.telPaisCod + "-" + this.telAreaCod + "-"+ this.telefono;
	}
	
	public String getCelularCompleto(){
		if (this.celPaisCod != null && !this.celPaisCod.equals("")){
			return this.celPaisCod + "-" + this.celAreaCod + "-"+ this.celular;
		} else {
			return "";
		}
	}
	
	public String getFaxCompleto(){
		if (this.faxPaisCod != null && !this.faxPaisCod.equals("")){
			return this.faxPaisCod + "-" + this.faxAreaCod + "-"+ this.fax;
		} else {
			return "";
		}
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getDni() {
		return dni;
	}


	public void setMens(String mens) {
		this.mens = mens;
	}


	public String getMens() {
		return mens;
	}

}
