package com.aeeventos.core.bean;

import java.util.Date;
import java.util.List;

import com.aeeventos.core.bean.enums.EstadoAcreditacionEnum;
import com.aeeventos.core.dao.AcreditacionHome;



/**
 * Acreditacion generated by hbm2java
 */
public class Acreditacion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idAcreditacion;
	private Evento evento;
	private Asistente asistente;
	private EstadoAcreditacionEnum estadoAcreditacion;
	private String urlImagen;
	private boolean borrado;
	private String usuarioInsert;
	private Date fechaInsert;
	private String usuarioUpdate;
	private Date fechaUpdate;
	private Boolean mailEnviado;
	private Boolean datamatrixGenerada;

	public Acreditacion() {
		this.mailEnviado = null;
		this.datamatrixGenerada = null;
	}

	public Acreditacion(int idAcreditacion,
			EstadoAcreditacionEnum estadoAcreditacion, boolean borrado,
			String usuarioInsert, Date fechaInsert, boolean mailEnviado) {
		this.idAcreditacion = idAcreditacion;
		this.estadoAcreditacion = estadoAcreditacion;
		this.borrado = borrado;
		this.usuarioInsert = usuarioInsert;
		this.fechaInsert = fechaInsert;
		this.mailEnviado = mailEnviado;
	}

	public Acreditacion(int idAcreditacion, Evento evento, Asistente asistente,
			EstadoAcreditacionEnum estadoAcreditacion, String urlImagen,
			boolean borrado, String usuarioInsert, Date fechaInsert,
			String usuarioUpdate, Date fechaUpdate, boolean mailEnviado,
			Boolean datamatrixGenerada) {
		this.idAcreditacion = idAcreditacion;
		this.evento = evento;
		this.asistente = asistente;
		this.estadoAcreditacion = estadoAcreditacion;
		this.urlImagen = urlImagen;
		this.borrado = borrado;
		this.usuarioInsert = usuarioInsert;
		this.fechaInsert = fechaInsert;
		this.usuarioUpdate = usuarioUpdate;
		this.fechaUpdate = fechaUpdate;
		this.mailEnviado = mailEnviado;
		this.datamatrixGenerada = datamatrixGenerada;
	}

	public int getIdAcreditacion() {
		return this.idAcreditacion;
	}

	public void setIdAcreditacion(int idAcreditacion) {
		this.idAcreditacion = idAcreditacion;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Asistente getAsistente() {
		return this.asistente;
	}

	public void setAsistente(Asistente asistente) {
		this.asistente = asistente;
	}

	public EstadoAcreditacionEnum getEstadoAcreditacion() {
		return this.estadoAcreditacion;
	}

	public void setEstadoAcreditacion(EstadoAcreditacionEnum estadoAcreditacion) {
		this.estadoAcreditacion = estadoAcreditacion;
	}

	public String getUrlImagen() {
		return this.urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public boolean isBorrado() {
		return this.borrado;
	}

	public void setBorrado(Boolean borrado) {
		this.borrado = borrado;
	}

	public String getUsuarioInsert() {
		return this.usuarioInsert;
	}

	public void setUsuarioInsert(String usuarioInsert) {
		this.usuarioInsert = usuarioInsert;
	}

	public Date getFechaInsert() {
		return this.fechaInsert;
	}

	public void setFechaInsert(Date fechaInsert) {
		this.fechaInsert = fechaInsert;
	}

	public String getUsuarioUpdate() {
		return this.usuarioUpdate;
	}

	public void setUsuarioUpdate(String usuarioUpdate) {
		this.usuarioUpdate = usuarioUpdate;
	}

	public Date getFechaUpdate() {
		return this.fechaUpdate;
	}

	public void setFechaUpdate(Date fechaUpdate) {
		this.fechaUpdate = fechaUpdate;
	}

	public Boolean isMailEnviado() {
		return this.mailEnviado;
	}

	public void setMailEnviado(Boolean mailEnviado) {
		this.mailEnviado = mailEnviado;
	}

	public Boolean isDatamatrixGenerada() {
		return this.datamatrixGenerada;
	}

	public void setDatamatrixGenerada(Boolean datamatrixGenerada) {
		this.datamatrixGenerada = datamatrixGenerada;
	}

	public static List<Acreditacion> findByExample(Acreditacion filter) {
		return AcreditacionHome.getInstance().findByExample(filter);
	}

	public static List<Acreditacion> findByExampleEnviarMails(
			Acreditacion filter) {
		return AcreditacionHome.getInstance().findByExampleEnviarMails(filter);
	}

	public static Acreditacion findById(int AcreditacionId) {
		return AcreditacionHome.getInstance().findById(AcreditacionId);
	}

	public void cambiarEstado(EstadoAcreditacionEnum estado,
			Usuario usuarioLogueado) throws RuntimeException {
		this.setEstadoAcreditacion(estado);
		this.setUsuarioUpdate(usuarioLogueado.getUserName());
		this.setFechaUpdate(new Date());
		AcreditacionHome.getInstance().Update(this);
	}

	public void mailEnviado() {
		this.setMailEnviado(true);
		AcreditacionHome.getInstance().Update(this);
	}

	/**
	 * 
	 * @param acreditacion
	 *            que vamos a actualizar
	 */

	public  void datamatrixGenerada() {
		this.setDatamatrixGenerada(true);
		AcreditacionHome.getInstance().Update(this);

	}

	public void insert(Usuario insert) {
		
		if (insert!=null)
		{
			this.setUsuarioInsert(insert.getUserName());
		}
		
		AcreditacionHome.getInstance().persist(this);

	}
	
	public Acreditacion validarAcceso(Acreditacion acreditacion)
	{
		return AcreditacionHome.getInstance().validarAcceso(acreditacion);
	}

	public String getUrlImagenDatamatrix() {
		return this.evento.getNombre().concat("-").concat(this.asistente.getDni()).concat(".png");
	}
}
