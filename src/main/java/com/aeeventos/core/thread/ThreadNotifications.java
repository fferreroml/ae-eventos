package com.aeeventos.core.thread;

import java.util.List;
import org.apache.log4j.Logger;

import com.aeeventos.core.bean.Acreditacion;
import com.aeeventos.util.AcreditacionesMailGenerator;


public class ThreadNotifications implements Runnable {

	private final static Logger log = Logger
			.getLogger(ThreadNotifications.class);

	private List<Acreditacion> listaAcreditado;
	private String contexto;

	/**
	 * @param listaAcreditado
	 * @param contexto del evento
	 */
	public ThreadNotifications(List<Acreditacion> listaAcreditado,
			String contexto) {
		this.listaAcreditado = listaAcreditado;
		this.contexto = contexto;
	}

	public void run() {
		for (Acreditacion acreditacion : listaAcreditado){
			boolean mailSentOk = AcreditacionesMailGenerator.mailGenerator(acreditacion, contexto);
			if(!mailSentOk){
				log.error("error al enviar el email a: " + acreditacion.getAsistente().getDni());
			}
		}
	}
}
