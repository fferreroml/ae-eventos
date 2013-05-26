package com.aeeventos.core.webapps.action
;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.Session;

import com.aeeventos.core.bean.Usuario;
import com.aeeventos.core.webapps.form.HomeForm;
import com.aeeventos.hibernate.HibernateUtil;




/**
 * Controlador para la vista del Home.
 * */
public class HomeAction extends DispatchAction {

	/**
	 * Este metodo inicializa el el home y redirecciona a la pagina de login.
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
	 * */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HomeForm homeForm = (HomeForm) form;
		homeForm.cleanForm();

		request.getSession().removeAttribute("AcreditacionesUser");

		return mapping.findForward("login");

	}

	/**
	 * Este metodo realiza el login y redirecciona a la pagina de home.
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
	 * */
	public ActionForward onLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HomeForm homeForm = (HomeForm) form;
		
		try {
			Usuario usuario = new Usuario();
			usuario.setUserName(homeForm.getUserName());
			usuario.setPassword(homeForm.getPassword());
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			usuario = Usuario.loguearUsuario(usuario);
			session.getTransaction().commit();
			
			if (usuario != null){
				request.getSession().setAttribute("AcreditacionesUser", usuario);
				return mapping.findForward("usrHome");
			} else {
				setMessage(request, "home.login.invalidUser");
				return init(mapping, homeForm, request, response);
			}
		} catch ( RuntimeException e) {
			setMessage(request, "home.login.HibernateError");
			return init(mapping, homeForm, request, response);
		}
		
	}
	
	public void setMessage(HttpServletRequest request, String key) {
		ActionMessages msg = new ActionMessages();
		msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key));
		saveMessages(request, msg);
	}

}
