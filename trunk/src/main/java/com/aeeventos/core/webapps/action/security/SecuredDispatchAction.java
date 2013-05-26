package com.aeeventos.core.webapps.action.security;

import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.aeeventos.core.bean.Usuario;


/**
 * Esta Clase es intermedia entre los Controladores de la Aplicación y el
 * Controlador General de Struts {@link DispatchAction}. De esta manera se puede
 * procesar la petición antes de ingresar a la ejecución de cualquier método del
 * controlador.
 * */
public class SecuredDispatchAction extends DispatchAction {

	/**
	 * Generic Logger for the class.
	 * */
	private final Logger log = Logger.getLogger(SecuredDispatchAction.class);

	/**
	 * Este método se ejecuta siempre que se realiza una llamada desde la vista
	 * a un controlador de la aplicación.
	 * 
	 * @return {@link ActionForward} url con la pagina que debe retornar al
	 *         explorador.
	 * @param mapping
	 *            struts class.
	 * @param form
	 *            struts class.
	 * @param request
	 *            server class.
	 * @param response
	 *            server class.
	 * @throws Exception
	 *             - Exepción General.
	 * */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info(this.getMessageStartLog(mapping, request));
		
		//TODO cambiar

		Usuario usuario = (Usuario) request.getSession()
				.getAttribute("AcreditacionesUser");
		if (usuario == null) {

			return mapping.findForward("inicializar");

		}
		return super.execute(mapping, form, request, response);
	}

	/**
	 * Este método privado se usa para obtener el mensaje a logear en log4j de
	 * ingreso a cada metodo y action.
	 * 
	 * @return String con el mensaje a logear.
	 * @param mapping
	 *            struts class.
	 * @param request
	 *            server class.
	 * */
	private String getMessageStartLog(ActionMapping mapping,
			HttpServletRequest request) {
		String parameter = mapping.getParameter();
		String controller = mapping.getPath().substring(1);
		String method = request.getParameter(parameter);

		StringBuffer str = new StringBuffer();
		str.append("Ejecutandose el método ").append(method);
		str.append(" en el Controlador ").append(controller);

		return str.toString();

	}

	/**
	 * Este metodo se utiliza para setar un mensaje desde el action y mostrarlo
	 * luego en el jsp.
	 * 
	 * @param request
	 *            objeto request del servidor web.
	 * @param key
	 *            clave de identificacion del mensaje en el archivo de recursos.
	 */
	public void setMessage(HttpServletRequest request, String key) {
		ActionMessages msg = new ActionMessages();
		msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key));
		saveMessages(request, msg);
	}

	/**
	 * Este metodo se utiliza para setar un mensaje desde el action y mostrarlo
	 * luego en el jsp.
	 * 
	 * @param request
	 *            objeto request del servidor web
	 * @param key
	 *            , clave de identificacion del mensaje en el archivo de
	 *            recursos.
	 * @param param
	 *            , Paramtero del mensaje.
	 */
	public void setMessage(HttpServletRequest request, String key, String param) {
		ActionMessages msg = new ActionMessages();
		msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key, param));
		saveMessages(request, msg);
	}

	/**
	 * Este metodo se utiliza para setar un mensaje desde el action y mostrarlo
	 * luego en el jsp.
	 * 
	 * @param request
	 *            objeto request del servidor web.
	 * @param mensajes
	 *            clave de identificacion del mensaje en el archivo de recursos.
	 */
	public void setMessages(HttpServletRequest request, Set<String> mensajes) {
		ActionMessages msg = new ActionMessages();
		StringTokenizer str;
		for (String error : mensajes) {
			str = new StringTokenizer(error, "|");

			String key = str.nextToken();

			if (str.countTokens() >= 1) { // Viene un mensaje con parametros.
				Object[] params = new Object[str.countTokens()];
				int i = 0;
				while (str.hasMoreTokens()) {
					params[i] = str.nextToken();
					i++;
				}
				msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key,
						params));
			} else { // Mensaje sin parametros.
				msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key));
			}
		}
		saveMessages(request, msg);
	}

}
