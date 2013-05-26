<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link href="resources/styles/style.css" rel="stylesheet" type="text/css" />

<script>
function go(paramGo){
	document.location.href = paramGo;
}
</script>


 <div class="WrapperMenu">
         <div class="Menu">
               <ul>
                 <logic:equal name="AcreditacionesUser" property="rol.valor" value="0" scope="session">
		            <li><div onclick="javascript:go('AcreditacionAdmin.do?method=onListar');">Listado de Eventos</div></li>
		         </logic:equal>
		             
		         <logic:equal name="AcreditacionesUser" property="rol.valor" value="1" scope="session">
		            <li><div onclick="javascript:go('AcreditacionAdminEvento.do?method=onListarEventos');">Mis Eventos</div></li>
				 </logic:equal>
                </ul>
               </div>
           
 </div>

 