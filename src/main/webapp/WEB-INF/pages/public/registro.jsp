<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Formulario de Acreditación Conferencia</title>
<link
	href="resources/styles/evento<bean:write name='registroEventoForm' property='eventoRegistro.contexto'/>/estiloRegistro.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/js/common.js"
	language="javascript"></script>

<script type="text/javascript">

	var focusControl = null;
	function validarDatos(form) {

		var msnError = "";

		// Validacion del nombre
		if(isEmpty(form.dni.value)) {
			msnError = 
			 "<bean:message key="errors.requerido" arg0="Dni"/>"
			+ "\n";
			focusControl = form.dni;
			alert(msnError);
			return false;				
		} else {
			if(!isInteger(form.dni.value)) {
				msnError = 
				 "<bean:message key="errors.numeric" arg0="Dni"/>"
				+ "\n";
				focusControl = form.dni;	
				alert(msnError);
				return false;			
			}
		}

		// Validacion del nombre
		if(isEmpty(form.nombre.value)) {
			msnError = 
			 "<bean:message key="errors.requerido" arg0="Nombre"/>"
			+ "\n";
			focusControl = form.nombre;
			alert(msnError);
			return false;				
		} else {
			if(!isValidAlphanumeric(form.nombre.value)) {
				msnError = 
				 "<bean:message key="errors.alphanumeric" arg0="Nombre"/>"
				+ "\n";
				focusControl = form.nombre;	
				alert(msnError);
				return false;			
			}
		}

		
		// Validacion del Apellido
		if(isEmpty(form.apellido.value)) {
			msnError = 
			 "<bean:message key="errors.requerido" arg0="Apellido"/>"
			+ "\n";
			focusControl = form.apellido;
			alert(msnError);
			return false;				
		} else {
			if(!isValidAlphanumeric(form.apellido.value)) {
				msnError = 
				 "<bean:message key="errors.alphanumeric" arg0="Apellido"/>"
				+ "\n";
				focusControl = form.apellido;	
				alert(msnError);
				return false;			
			}
		}

		// Validacion del Cargo
		if(isEmpty(form.cargo.value)) {
			msnError = 
			 "<bean:message key="errors.requerido" arg0="Cargo"/>"
			+ "\n";
			focusControl = form.cargo;
			alert(msnError);
			return false;				
		} else {
			if(!isValidAlphanumeric(form.cargo.value)) {
				msnError = 
				 "<bean:message key="errors.alphanumeric" arg0="Cargo"/>"
				+ "\n";
				focusControl = form.cargo;	
				alert(msnError);
				return false;			
			}
		}
		
		// Validacion del telefono Fijo
		if(isEmpty(form.telPaisCod.value) || isEmpty(form.telAreaCod.value) || isEmpty(form.telefono.value)) {
			msnError = "Debe especificar los tres campos correspondientes al teléfono de contacto"
			+ "\n";
			focusControl = form.telPaisCod;
			alert(msnError);
			return false;
		} else {
			if(!isInteger(form.telPaisCod.value) || !isInteger(form.telAreaCod.value) || !isInteger(form.telefono.value)) {
				msnError = 
				 "<bean:message key="errors.numeric" arg0="Teléfono"/>"
				+ "\n";
				focusControl = form.telPaisCod;
				alert(msnError);
				return false;
			}
		}

		// Validacion del Celular
		//var celu;
		if(isEmpty(form.celPaisCod.value) && isEmpty(form.celAreaCod.value) && isEmpty(form.celular.value)) {
			// Jejejej no hace nada.
		} else {
			if(isEmpty(form.celPaisCod.value) || isEmpty(form.celAreaCod.value) || isEmpty(form.celular.value)) {
				msnError = "Debe especificar los tres campos correspondientes al teléfono celular o ninguno de ellos"
					+ "\n";
					focusControl = form.celPaisCod;
					alert(msnError);
					return false;
			} else {
				if(!isInteger(form.celPaisCod.value) || !isInteger(form.celAreaCod.value) || !isInteger(form.celular.value)) {
					msnError = 
					 "<bean:message key="errors.numeric" arg0="Celular"/>"
					+ "\n";
					focusControl = form.celPaisCod;
					alert(msnError);
					return false;
				}
			}	
		}

		// Validacion del Fax
		//var celu;
		if(isEmpty(form.faxPaisCod.value) && isEmpty(form.faxAreaCod.value) && isEmpty(form.fax.value)) {
			// Jejejej no hace nada.
		} else {
			if(isEmpty(form.faxPaisCod.value) || isEmpty(form.faxAreaCod.value) || isEmpty(form.fax.value)) {
				msnError = "Debe especificar los tres campos correspondientes al fax o ninguno de ellos"
					+ "\n";
					focusControl = form.faxPaisCod;
					alert(msnError);
					return false;
			} else {
				if(!isInteger(form.faxPaisCod.value) || !isInteger(form.faxAreaCod.value) || !isInteger(form.fax.value)) {
					msnError = 
					 "<bean:message key="errors.numeric" arg0="Fax"/>"
					+ "\n";
					focusControl = form.faxPaisCod;
					alert(msnError);
					return false;
				}
			}	
		}

		// Validación del Email
		if(isEmpty(form.email.value)) {
			msnError = 
			 "<bean:message key="errors.requerido" arg0="eMail"/>"
			+ "\n";
			focusControl = form.email;
			alert(msnError);
			return false;
		} else {
			if(!isValidEmail(form.email.value)) {
				msnError = 
				 "<bean:message key="errors.email" arg0="eMail"/>"
				+ "\n";
				focusControl = form.email;
				alert(msnError);
				return false;
			}
		}

		// Validación del Email Confirm
		if(isEmpty(form.emailConfirm.value)) {
			msnError = 
			 "<bean:message key="errors.requerido" arg0="Confirmación de eMail"/>"
			+ "\n";
			focusControl = form.emailConfirm;
			alert(msnError);
			return false;
		} else {
			if(!isValidEmail(form.emailConfirm.value)) {
				msnError = 
				 "<bean:message key="errors.email" arg0="Confirmación de eMail"/>"
				+ "\n";
				focusControl = form.emailConfirm;
				alert(msnError);
				return false;
			}
		}

		// Validacion de que sean iguales
		if (form.emailConfirm.value != form.email.value){
			msnError ="El campo eMail y la Confirmación del mismo deben ser identicos"
				+ "\n";
				focusControl = form.email;
				alert(msnError);
				return false;
		}

		// Validacion de la Direccion
		if(isEmpty(form.direccion.value)) {
			msnError = 
			 "<bean:message key="errors.requerido" arg0="Dirección"/>"
			+ "\n";
			focusControl = form.direccion;
			alert(msnError);
			return false;				
		} else {
			if(!isValidAlphanumeric(form.direccion.value)) {
				msnError = 
				 "<bean:message key="errors.alphanumeric" arg0="Dirección"/>"
				+ "\n";
				focusControl = form.direccion;	
				alert(msnError);
				return false;			
			}
		}

		// Validacion de la Ciudad
		if(isEmpty(form.ciudad.value)) {
			msnError = 
			 "<bean:message key="errors.requerido" arg0="Ciudad"/>"
			+ "\n";
			focusControl = form.ciudad;
			alert(msnError);
			return false;				
		} else {
			if(!isValidAlphanumeric(form.ciudad.value)) {
				msnError = 
				 "<bean:message key="errors.alphanumeric" arg0="Ciudad"/>"
				+ "\n";
				focusControl = form.ciudad;	
				alert(msnError);
				return false;			
			}
		}		
		return true;
	}

	function onGuardarRegistro(id) {
		if(!validarDatos(document.registroEventoForm))
		{
			return;
		}
		else
		{
		 document.registroEventoForm.method.value="onGuardarRegistro";
		 document.registroEventoForm.idEvento.value = id;
		 onSubmitStruts('RegistroEvento','registroEventoForm', 'onGuardarRegistro');
		 }
	}


	</script>

</head>
<body>
<div id="wrapper">
<div id="header"><img
	src="resources/images/eventos<bean:write name='registroEventoForm' property='eventoRegistro.contexto'/>/header_top.jpg"
	width="780" height="107" /></div>
<div id="content">
<h1>Formulario de Inscripción</h1>

<div class="col"><html:form action="RegistroEvento"
	onsubmit="return validarDatos(this);" focus="dni">
	<html:hidden property="method" value="" />
	<html:hidden name="registroEventoForm" property="idEvento" />
			
     Dni *<br />
	<label> <html:text name="registroEventoForm" property="dni"
		styleClass="input_box" maxlength="160"
		title="Dni del Asistente al Evento" /> </label>
		
	<br />
			
            Nombre *<br />
	<label> <html:text name="registroEventoForm" property="nombre"
		styleClass="input_box" maxlength="160"
		title="Nombre del Asistente al Evento" /> </label>
		
	<br />
	Apellido *  <br />
	<html:text name="registroEventoForm" property="apellido"
		styleClass="input_box" maxlength="120"
		title="Apellido del Asistente en la Empresa" />
	<p>Ocupacion *  <br />
	<html:text name="registroEventoForm" property="cargo"
		styleClass="input_box" maxlength="120"
		title="Cargo del Asistente en la Empresa" />
	<p>Compañía <br />
	<html:text name="registroEventoForm" property="compania"
		styleClass="input_box" maxlength="120" title="Compañia del Asistente" />
	</p>
	<p>Teléfono de contacto *  <br />
	<html:text name="registroEventoForm" property="telPaisCod"
		styleClass="input_cod" maxlength="4" title="Código del Pais" /> <html:text
		name="registroEventoForm" property="telAreaCod" styleClass="input_cod"
		maxlength="10" title="Código del Area" /> <html:text
		name="registroEventoForm" property="telefono" styleClass="input_muner"
		maxlength="20" title="Número Telefonico" /></p>
	<p>Celular<br />
	<html:text name="registroEventoForm" property="celPaisCod"
		styleClass="input_cod" maxlength="4" title="Código del Pais" /> <html:text
		name="registroEventoForm" property="celAreaCod" styleClass="input_cod"
		maxlength="10" title="Código del Area" /> <html:text
		name="registroEventoForm" property="celular" styleClass="input_muner"
		maxlength="20" title="Número del Telefono Móvil" /></p>
	<p>Fax<br />
	<html:text name="registroEventoForm" property="faxPaisCod"
		styleClass="input_cod" maxlength="4" title="Código del Pais" /> <html:text
		name="registroEventoForm" property="faxAreaCod" styleClass="input_cod"
		maxlength="10" title="Código del Area" /> <html:text
		name="registroEventoForm" property="fax" styleClass="input_muner"
		maxlength="20" title="Número de Fax" /></p></div>
<div class="col">Email *  <br />
<html:text name="registroEventoForm" property="email"
	styleClass="input_box" maxlength="180"
	title="Dirección de mail del Asistente" />

<p>Confirmación de Email *  <br />
<html:text name="registroEventoForm" property="emailConfirm"
	styleClass="input_box" maxlength="180"
	title="Dirección de mail del Asistente" /></p>
<p>Dirección *  <br />
<html:text name="registroEventoForm" property="direccion"
	styleClass="input_box" maxlength="180"
	title="Dirección de Residencia del Asistente" /></p>
<p>Ciudad * <br />
<html:text name="registroEventoForm" property="ciudad"
	styleClass="input_box" maxlength="160"
	title="Ciudad de Residencia del Asistente" /></p>
	
	        		<logic:messagesPresent message="true">
					<html:messages id="mess" message="true">
						 	
						 	<script  type="text/javascript"> 	
						 	
								alert("<bean:write name="mess" />");
						 	</script>

					</html:messages>
				</logic:messagesPresent>

<input value="Enviar" type="button"
	onclick="javascript:onGuardarRegistro(<bean:write name='registroEventoForm' property='idEvento'/> )">
</input></div>
</html:form>


<div class="clear"><strong>IMPORTANTE:</strong> Para inscribir 4 o
más asistentes, <a
	href="http://www.e-commercelavisiondeloslideres.cl/index.php?option=com_alfcontact&Itemid=30&lang=es"
	target="_blank">PERMÍTANOS CONTACTARLE</a><br />
Call Center: (0056) 2 6714957</div>
</div>

<div id="footer"></div>
</div>


</body>
</html:html>
