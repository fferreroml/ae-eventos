package aeeventos.core.bean.enums;

import java.util.ArrayList;
import java.util.List;


public enum EstadoAcreditacionEnum {
	
	 RECHAZADA(0,"RECHAZADO"), REGISTRADO(1,"REGISTRADO"), ACREDITADO(2, "ACREDITADO"), 
	 PENDIENTE_ACREDITACION(3, "PENDIENTE DE ACREDITACION");
		

		/**
		 * Constructor de la enumeracion EstadoAcreditacionEnum.
		 *
		 * @param valor
		 * @return descripcion
		 */
		
	 EstadoAcreditacionEnum(int valor, String descripcion)
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
		
		public static List<EstadoAcreditacionEnum> getAll(){		
			List<EstadoAcreditacionEnum> estados = new ArrayList<EstadoAcreditacionEnum>();
			estados.add(EstadoAcreditacionEnum.RECHAZADA);
			estados.add(EstadoAcreditacionEnum.REGISTRADO);
			estados.add(EstadoAcreditacionEnum.ACREDITADO);
			estados.add(EstadoAcreditacionEnum.PENDIENTE_ACREDITACION);
			
			return estados;		
		}


}
