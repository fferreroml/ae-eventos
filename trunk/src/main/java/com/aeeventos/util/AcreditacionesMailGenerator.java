package com.aeeventos.util;

import java.io.IOException;
import java.util.ResourceBundle;

import com.aeeventos.core.bean.Acreditacion;
import com.aeeventos.core.bean.enums.EstadoAcreditacionEnum;
import com.aeeventos.datamatrix.AcreditacionesDatamarixHelper;
import com.aeeventos.mailing.controlador.MailSender;
import com.aeeventos.mailing.modelo.DatamatrixEmail;
import com.aeeventos.mailing.modelo.Email;

public class AcreditacionesMailGenerator {
	private static final String DATAMATRIX_FOLDER_KEY = "evento.imagenes.datamatrix.ubicacion";

	public static boolean mailGenerator(Acreditacion acreditacion, String contexto) {

		try {
			ResourceBundle resources = ResourceBundle.getBundle("resources");
			contexto += resources.getString(DATAMATRIX_FOLDER_KEY).concat(acreditacion.getUrlImagenDatamatrix());
			
			if (!acreditacion.isDatamatrixGenerada()
					&& acreditacion.getEstadoAcreditacion() == EstadoAcreditacionEnum.ACREDITADO) {
	
				AcreditacionesDatamarixHelper.generateDatamatrix(acreditacion,
						contexto);
				acreditacion.datamatrixGenerada();
			}
			if (acreditacion.isDatamatrixGenerada()
					&& acreditacion.getEstadoAcreditacion() == EstadoAcreditacionEnum.ACREDITADO) {
				sendMailAcreditacion(acreditacion, contexto);
				acreditacion.mailEnviado();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static void sendMailAcreditacion(Acreditacion acreditacion, String contexto) throws Exception {

		
		 DatamatrixEmail email = new DatamatrixEmail();
		 email.addRecipient(acreditacion.getAsistente().getEmail());
		
		 //TODO ver como setearlo
	//	 email. setTo(acreditacion.getAsistente().getNombreCompleto());
		
		 email.setDatamatrixPath(contexto);
		 email.addReplacement("nombre-evento", acreditacion.getEvento().getNombre());
		 email.addReplacement("descripcion", acreditacion.getEvento().getDescripcion());
		 email.addReplacement("fecha", acreditacion.getEvento().getFecha().toString());
		 email.addReplacement("nombre", acreditacion.getAsistente().getNombre());
		 //TODO ver direccion
		 email.addReplacement("direccion", "");
		 email.addReplacement("hora", "");
		 
		 MailSender sender = new MailSender();
		 sender.send(email);

	}

}
