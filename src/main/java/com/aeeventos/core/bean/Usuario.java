package com.aeeventos.core.bean;



import java.util.HashSet;
import java.util.Set;

import com.aeeventos.core.bean.enums.RolEnum;
import com.aeeventos.core.dao.UsuarioHome;





/**
 * Usuario generated by hbm2java
 */
public class Usuario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idUsuario;
	private String nombre;
	private String apellido;
	private String correo;
	private String direccion;
	private String password;
	private boolean borrado;
	private String userName;
	
	private RolEnum rol;

	private Set<Evento> eventos = new HashSet<Evento>(0);


	public Usuario() {
	}

	public Usuario(int idUsuario, String nombre, String apellido,
			String correo, boolean borrado, RolEnum rol) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.borrado = borrado;
		this.rol = rol;
	}

	public Usuario(int idUsuario, String nombre, String apellido,
			String correo, String direccion, String password, boolean borrado,
			String userName,  RolEnum rol, Set<Evento> eventos, Set<Evento> eventos_1) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.direccion = direccion;
		this.password = password;
		this.borrado = borrado;
		this.userName = userName;
		this.rol = rol;
		this.eventos = eventos;

	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isBorrado() {
		return this.borrado;
	}

	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public  RolEnum getRol() {
		return this.rol;
	}

	public void setRol( RolEnum rol) {
		this.rol = rol;
	}

	public Set<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}
	
	public static Usuario loguearUsuario(Usuario user)  {
		return UsuarioHome.getInstance().loguearUsuario(user);
	}




}
