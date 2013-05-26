package aeeventos.core.webapps.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import aeeventos.core.bean.Acreditacion;
import aeeventos.core.bean.Asistente;
import aeeventos.core.bean.Evento;
import aeeventos.core.bean.Usuario;
import aeeventos.core.bean.enums.EstadoAcreditacionEnum;
import aeeventos.core.webapps.form.ControlAccesoForm;



public class ControlAccesoAction extends DispatchAction {
	
	/**
	 * Permite inicializar el control de acceso tomando por parametro el evento para el cual se va
	 * a controlar el acceso.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	public ActionForward onInicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ControlAccesoForm controlAccesoForm = (ControlAccesoForm) form;
		controlAccesoForm.cleanForm();

		try {
			String  eventoContexto =(request.getParameter("evento")); 
			
			Evento evt = new Evento();
			evt.setContexto("/".concat(eventoContexto));
		    evt= Evento.findByExample(evt).get(0);
		    controlAccesoForm.setId((evt.getIdEvento()));
		    controlAccesoForm.setEventoControl(evt);
		    setMessage(request, "controlAccesso.mensajeInicio");	
		    
		    return mapping.findForward("controlAcceso");
		} catch (RuntimeException re) {
			setMessage(request, "controlAcceso.hibernateError");
			return mapping.findForward("controlAcceso");
		}
		catch (Exception e) {
			setMessage(request, "public.generalError");
			return mapping.findForward("controlAcceso");
		}
	}
	
	/**
	 * Metodo que realiza la validacion de la acreditacion controlando que este registrado como
	 * Acreditado, que este registrado para el evento en cuestion, realiza el envio de la foto 
	 * y se encarga de mostrar los datos de el asistente en caso de acreditacion valida y los 
	 * motivos por los cuales no se puede realizar la acreditacion en caso que sea invalida
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	public ActionForward onValidarAcceso(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ControlAccesoForm controlAccesoForm = (ControlAccesoForm) form;
		
		Usuario usuario = new Usuario();
		usuario.setNombre("Control Acceso");

		Acreditacion acreditacion = new Acreditacion();

		try {
			Evento evt = new Evento();
		    evt= Evento.findById(controlAccesoForm.getId());

		    acreditacion.setEvento(evt);
		    
		    Asistente asi = new Asistente();
		    asi = Asistente.findByDni(controlAccesoForm.getDni());
		    
		    
		    /**
		     * En el caso de cambiar los datos con los q se codifican los datamatrix
		     * habria que buscar una solucion, para el campo dni. Habria q ver dos caminos, uno 
		     * cuando lo ingresa a mano y otro cuando lee con la pistola de datamatrix
		     */

		    acreditacion.setAsistente(asi);
		    acreditacion = Acreditacion.findByExample(acreditacion).get(0);
		    acreditacion = acreditacion.validarAcceso(acreditacion);
		    
		    /*
		     * El contexto se guarda en la base de datos con la siguiente estructura
		     *  {/Evento} por lo tanto debemos sacarle la barra al setearlo en el form, ya que 
		     *  cuando se lo pasamos a las RESTS URL debemos quitarle la misma.
		     */
		    String[] arrayContexto = evt.getContexto().split("/");

		    
		    controlAccesoForm.setEvento(arrayContexto[1]);
		    controlAccesoForm.setEventoControl(evt);
		    controlAccesoForm.setAsistente(asi);
		    controlAccesoForm.setUrlImagen(acreditacion.getUrlImagen());
		    
			controlAccesoForm.setDni("");
			if(acreditacion!=null)
			{
				switch (acreditacion.getEstadoAcreditacion()) {
				
				case ACREDITADO:
					acreditacion.cambiarEstado(EstadoAcreditacionEnum.REGISTRADO, usuario);
					controlAccesoForm.setAsistente(acreditacion.getAsistente());
					return mapping.findForward("controlAcceso");		
				case REGISTRADO: setMessage(request, "controlAccesso.yaIngresado");		    break;
				case RECHAZADA:  setMessage(request, "controlAccesso.rechazada");		    break;
				default:         setMessage(request, "controlAccesso.invalido");			break;
			}
			
		  } else {
			  setMessage(request, "controlAccesso.dniInvalido");
		  }
					  
		    return mapping.findForward("controlAcceso");
		    
		} catch (RuntimeException re) {
			setMessage(request, "controlAcceso.hibernateError");
			log.error("error al obtener la acreditacion " + controlAccesoForm.getMail());
			return mapping.findForward("controlAcceso");
		}
		catch (Exception e) {
			setMessage(request, "public.generalError");
			return mapping.findForward("controlAcceso");
		}
	}
	
	/**
	 * Metodo para setear los action messages al JSP
	 * @param request
	 * @param key
	 */
	public void setMessage(HttpServletRequest request, String key) {
		ActionMessages msg = new ActionMessages();
		msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key));
		saveMessages(request, msg);
	}
	


	

}
