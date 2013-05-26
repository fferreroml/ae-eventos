package com.aeeventos.core.bean.enums;

/**
 * Enum para el manejo de roles de la aplicacion
 * @author fferrero
 */

public enum RolEnum {
	
	ADMINISTRADOR(0,"ADMINISTRADOR"), ADMINISTRADOR_EVENTO(1,"ADMINISTRADOR_EVENTO");
	

	/**
	 * Constructor de la enumeracion RolEnum.
	 *
	 * @param valor
	 * @return descripcion
	 */
	
	RolEnum(int valor, String descripcion)
	{
		this.valor = valor;
		this.descripcion = descripcion;
		
	}
	
	/**
	 * Valor asignado a cada item de la enumeracion.
	 */
	private int valor;
	
	/**
	 * Descripcion asignada a cada item de la enumeracion.
	 */
	private String descripcion;

	/**
	 * Retorna el valor  de la enumeracion
	 * @return el valor
	 */
	public int getValor() {
		return valor;
	}
	/** Retorna la descripcion de la eumeracion
	 * @return la descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}


}
