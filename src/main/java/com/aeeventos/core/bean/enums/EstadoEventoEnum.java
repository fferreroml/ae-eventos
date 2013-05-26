package com.aeeventos.core.bean.enums;

/**
 * Enum para el manejo de estados del evento
 */

public enum EstadoEventoEnum {
	
   EN_CREACION(0,"EN CREACION"), PUBLICADO(1,"PUBLICADO"), CERRADO(2, "CERRADO"), FINALIZADO(3,"FINALIZADO");
	

	/**
	 * Constructor de la enumeracion EstadoEventoEnum.
	 *
	 * @param valor
	 * @return descripcion
	 */
	
    EstadoEventoEnum(int valor, String descripcion)
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
