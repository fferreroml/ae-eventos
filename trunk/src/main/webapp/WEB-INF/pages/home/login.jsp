<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>Acreditaciones</title>
	
	<meta http-equiv="" content="text/html; charset=utf-8">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script>
	var focusControl = null;

	// Validacion de los datos al guardar
	function validateLoginForm(form) {
		var msnError = "";

		// Nombre de usuario
		if (isEmpty(form.userName.value)) {
			msnError = msnError
					+ "<bean:message key='errors.requerido' arg0='Usuario'/>"
					+ "\n";

			focusControl = form.userName;
		}

		// Clave de usuario
		if (isEmpty(form.password.value)) {
			msnError = msnError
					+ "<bean:message key='errors.requerido' arg0='Password'/>"
					+ "\n";
			if (focusControl == null ){
				focusControl = form.password;
			}
		}		

		
		// Show the message
		if (isEmpty(msnError)) {					
			return true;
		} else {
			alert(msnError);
			return false;
		}
	}
	

	</script>
	
	<link rel="stylesheet" href="resources/styles/styles.css" type="text/css" />
	<script type="text/javascript" src="resources/js/common.js"
	language="javascript"></script>
	
</head>

<body>
<div class="LogoLogin">
<img src="resources/images/logo_login.png"  width="100%" height="208" />
</div>

<logic:messagesPresent message="true">
	<div align="center"><html:messages id="mess" message="true">
		<script>
				alert('<bean:write name="mess"/>');
		</script>
	</html:messages></div>
</logic:messagesPresent>

<div class="ContentLogin">
Bienvenidos a <b>ACREDITACIONES</b><br />
Por favor, ingresa el Nombre de Usuario y su Password:<br />

	<html:form 
			action="Home" 
			onsubmit="return validateLoginForm(this);"
			focus="userName">
			<html:hidden property="method" value="" />
			
      <div class="BoxLogin">
            <table width="" border="0" cellspacing="8" cellpadding="0">
            <tr>
            <td>Usuario: </td>
            <td>
            <html:text  name="homeForm"
						property="userName" styleClass="InputLogin" maxlength="30"
						onkeypress="javascript:textEnterClickBtnValidation(event, 'Home', 'homeForm', 'onLogin')">
			</html:text>
			</td>
            </tr>
            <tr>
            <td>Password: </td>
            <td>  
             <html:password name="homeForm"
						    property="password" maxlength="30" styleClass="InputLogin"
						    onkeypress="javascript:textEnterClickBtnValidation(event, 'Home', 'homeForm', 'onLogin')">
			</html:password>
			</td>
            </tr>
            <tr>
            <td>&nbsp;</td>
            <td>
			<div id="saveButton" align="right" class="BtnLogin"
			onclick="javascript:onSubmitStrutsValidate('Home', 'homeForm', 'onLogin')"></div>
			</td>
            
            </tr>
            </table>
      </div>
      </html:form>
</div>

</body>
</html>
