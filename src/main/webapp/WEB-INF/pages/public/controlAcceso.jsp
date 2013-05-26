<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<link rel="stylesheet" href="resources/styles/stylesTotem.css" type="text/css" />
<link rel="stylesheet" href="resources/styles/style_acordion.css" type="text/css" />
<script type="text/javascript" src="resources/js/jquery-1.2.6.min.js"></script>
<script type="text/javascript"
	src="resources/js/jquery-fieldselection.js"></script>
<script type="text/javascript" src="resources/js/script.js"></script>
<script type="text/javascript" src="resources/js/common.js"
	language="javascript"></script>

<script>
var focusControl = null;
var aux=null;
function press(val) {
      if( val == 'borrar' ) {
	      $('#pwd').replaceSelection("", true);
      }   
      else if (val == 'limpiar') {
    	  document.controlAccesoForm.dni.value = "";
      } else {
	      $('#pwd').replaceSelection(val, true);
      }
}

function validarDatos (form) {

	// Validación del dni Confirm
		if(isEmpty(form.dni.value)) {
			msnError = 
			 "<bean:message key="errors.datos"/>"
			+ "\n";
			focusControl = form.dni;
			alert(msnError);
			return false;
		}
		else {
			if(!isInteger(form.dni.value)) {
				msnError = 
				 "<bean:message key="errors.numeric" arg0="Dni"/>"
				+ "\n";
				focusControl = form.dni;	
				alert(msnError);
				return false;			
			}
		}
	
	
	document.controlAccesoForm.method.value = 'onValidarAcceso';
	return true;
}

function onListo (id) {
 if (validarDatos(document.controlAccesoForm)) { 
	document.controlAccesoForm.method.value = 'onValidarAcceso';
	document.controlAccesoForm.id.value = id;
	document.controlAccesoForm.submit();
 }
}

function delay(url){
	 aux = url;
    var b =  setTimeout("afterFiveSeconds(aux)",2000);

}

function afterFiveSeconds(url){
	 document.getElementById("imagenLogo").src = "resources/images/fotoAsistentes/"+url;
	 return url;

}

</script>





<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Control de Acceso / <bean:write name='controlAccesoForm' property='nombre'/></title>
</head>

<body>
<html:form action="ControlAcceso" onsubmit="return validarDatos(this);"
	focus="dni">
	<html:hidden property="method" value="" />
	<html:hidden name="controlAccesoForm" property="id" />
<div class="box">
      <div class="logo">
      </div>
      <div class="logo_evento">
            <img src="resources/images/eventos<bean:write name='controlAccesoForm' property='eventoControl.contexto'/>/logo_evento.jpg"  align="middle" />
      </div>
      
      <div class="content">
            <div class="text_acreditacion">
                  <img src="resources/images/t_bienvenido.gif" width="183" height="28" />
                  <br />
                  	<logic:messagesPresent message="true">
					<html:messages id="mess" message="true">
							<bean:write name="mess" />
					</html:messages>
				</logic:messagesPresent>

				<logic:messagesNotPresent message="true">
				
				 <span>Apellido:</span><span>&nbsp;&nbsp;<bean:write name="controlAccesoForm" property="asistente.apellido"/><br/>
         		 </span><span >Nombre:</span><span>&nbsp;&nbsp;<bean:write name="controlAccesoForm" property="asistente.nombre"/><br/>
        		 </span><span >Empresa:</span><span>&nbsp;&nbsp;<bean:write name="controlAccesoForm" property="asistente.compania"/> <br/>
       	         </span><span >Cargo:</span><span>&nbsp;&nbsp;<bean:write name="controlAccesoForm" property="asistente.cargo"/></span><br/>
				</logic:messagesNotPresent>
				
							
            </div>
            <logic:messagesPresent message="true">
            <div class="acceso_acreditacion">
                  <img id="imagenLogo" src="resources/images/img_acceso_denegado.jpg" width="130" height="115" />

            </div>
            </logic:messagesPresent>
            <logic:messagesNotPresent message="true">
               <div  class="acceso_acreditacion">
                  <img id="imagenLogo" src="resources/images/img_acceso_aprobado.jpg" width="130" height="115" />
                  <script type="text/javascript">
                  delay("<bean:write name="controlAccesoForm" property="urlImagen"/>");
                  </script>
            </div>
            </logic:messagesNotPresent>
            
            
            
            
            
<div id="acc">
	<div>
		<h3></h3>
		      <table style="margin-left: 0px; margin-top: 0px; margin-bottom: -15px;" width="200" border="0" cellspacing="20">
		      <tr>	      
		      <td><html:text name="controlAccesoForm" property="dni" styleClass="input_dni" size="32" styleId="pwd" /></td>
		      <td><input class="btn_listo" type="button" name="" id="listo" onclick="onListo(<bean:write name='controlAccesoForm' property='id'/>)"/></td>
		      <td><input class="btn_limpiar" type="button" name="" id="limpiar" onclick="press('limpiar')"/></td>
		      </tr>
		      </table>
		      
		<div class="campo_dni">
            <table style="margin-left: -5px; margin-top: 4px;" width="200" border="0" cellspacing="10">
            <tr>
            <td><input class="btn_1" type="button" name="" id="btn_1" onclick="press('1')"/></td>
            <td><input class="btn_2" type="button" name="" id="btn_2" onclick="press('2')"/></td>
            <td><input class="btn_3" type="button" name="" id="btn_3" onclick="press('3')"/></td>
            <td><input class="btn_4" type="button" name="" id="btn_4" onclick="press('4')"/></td>
            <td><input class="btn_5" type="button" name="" id="btn_5" onclick="press('5')"/></td>
            <td><input class="btn_6" type="button" name="" id="btn_6" onclick="press('6')"/></td>
            <td><input class="btn_7" type="button" name="" id="btn_7" onclick="press('7')"/></td>
            <td><input class="btn_8" type="button" name="" id="btn_8" onclick="press('8')"/></td>
            <td><input class="btn_9" type="button" name="" id="btn_9" onclick="press('9')"/></td>
            <td><input class="btn_0" type="button" name="" id="btn_0" onclick="press('0')"/></td>
            <td><input class="btn_borrar" type="button" name="" id="btn_borrar" onclick="press('borrar')" /></td>
            </tr>
            </table>
		</div>

	</div>
</div>
            
            
      </div>
</div>
<script type="text/javascript">

var parentAccordion=new TINY.accordion.slider("parentAccordion");
parentAccordion.init("acc","h3",-1,-1);

var nestedAccordion=new TINY.accordion.slider("nestedAccordion");
nestedAccordion.init("nested","h3",1,-1,"acc-selected");
</script>

</html:form>
</body>
</html>