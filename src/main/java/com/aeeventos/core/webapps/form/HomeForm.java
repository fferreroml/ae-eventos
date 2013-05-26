package com.aeeventos.core.webapps.form;

import org.apache.struts.action.ActionForm;



/**
 * Esta clase representa un formulario en el jsp relativo al home.
 * */
public class HomeForm extends ActionForm {

	/**
	 * Identificador por ser un objeto Serializable al extender de Action Form.
	 */
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;



	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public void cleanForm() {
		this.userName = "";
		this.password = "";
		
	}

	public boolean validateForm() {
		
		return true;
	}

}
