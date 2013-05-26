<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/display" prefix="display"%>


<tiles:insert definition="template.default" flush="true">
	<tiles:put name="titulo" type="string" value="Listado de Eventos" />

	<tiles:put name="body" type="string">
		<script>

		function inicializar()
		{
			document.acreditacionAdminForm.radio[0].checked = true;
		}

			function detalleEvento(evento){
				document.location.href = "AcreditacionAdmin.do?method=onListarDetalleEvento&evento="+evento;
			}
			function enviarNotificacion(evento){
				document.location.href = "AcreditacionAdmin.do?method=onEnviarMails&evento="+evento;
			}
			function cambiarEstadoEvento(estado){
				 var i;
				 var sel;
				    for (i=0;i<document.acreditacionAdminForm.radio.length;i++){
				       if (document.acreditacionAdminForm.radio[i].checked)
				       {
				    	   sel = document.acreditacionAdminForm.radio[i].value;
				    	   break;
				       }
				         
				    }

				    if(i<=document.acreditacionAdminForm.radio.length)
				    {document.location.href = "AcreditacionAdmin.do?method=onCambiarEstadoEvento&evento="+sel+"&estado="+estado;}
				    else
				    {
					    alert("Debe seleccionar el evento al desea cambiar el estado");
				    }
				} 

		</script>

	<div align="center" ><html:form action="/AcreditacionAdmin" >
			<html:hidden property="method" value="" />
			
			<table>
			<tr>
			<td> <div align="center" class="botonNUI" title="EN CREACION"  
			onclick="javascript:cambiarEstadoEvento('0')"></div></td>
			<td><div align="center" class="botonNUI" title="PUBLICADO"  
			onclick="javascript:cambiarEstadoEvento('1')"></div></td>
			<td><div align="center" class="botonNUI" title="CERRADO" 
			onclick="javascript:cambiarEstadoEvento('2')"/></div></td>
			<td><div align="center" class="botonNUI" title="FINALIZADO" 
			onclick="javascript:cambiarEstadoEvento('3')"></div></td>
			</tr>
			<tr>
			<td align="center">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; EN CREACION</td>
			<td align="center">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; PUBLICADO</td>
			<td align="center">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; CERRADO</td>
			<td align="center">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; FINALIZADO</td>
			</tr>
			

			</table>
			
			<div align="center" >
			<div align="right" ><br />
			<br />
			</div>

			<display:table excludedParams="*"
				requestURI="AcreditacionAdmin.do?method=onListar" pagesize="10"
				id="evento" name="acreditacionAdminForm.eventos"
				style="width: 100%; border=0;"  cellpadding="0" cellspacing="0" class="list"
				>
					
				<display:column
					style="width: 10%;height:18px;">
					<input type="radio"  name="radio"  id="radio" value="<bean:write name="evento" property="idEvento" />" />
				</display:column>

				<display:column 
					style="width: 30%;height:18px;"  
					title="Nombre">
					<a style="cursor: pointer;" title="Ver Detalle" onclick="javascript:detalleEvento(<bean:write name='evento' property='idEvento'/>)">
					<bean:write name="evento" property="nombre"/>
					</a>  
				</display:column>

				<display:column 
					style="width: 15%;height:18px;" 
					title="Fecha Inicio" property="fecha" decorator="com.acreditaciones.util.ConvertirFecha" >

				</display:column>
				
					<display:column 
					style="width: 15%;height:18px;"
					title="Fecha Cierre" property="fechaCierre" decorator="com.acreditaciones.util.ConvertirFecha" >

				</display:column>
    

				<display:column 
					style="width: 20%;height:18px" 
					title="Estado" property="estadoEvento.descripcion">

				</display:column>
				
				<display:column     title="Enviar Notificacion"
					style="width: 20%;height:18px;text-align:center;">
					<div align="center" class="">
					<table width="8%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12">
							<div align="right" class="botonSeleccionar" title="Enviar Notificacion Acreditados"
								onclick="javascript:enviarNotificacion(<bean:write name='evento' property='idEvento'/>)">
								</div>
							</td>
						</tr>
					</table>
					</div>
				</display:column>

				<display:column  title="Seleccionar"
					style="width: 22.5%;height:18px;text-align:center;">
					<div align="center" class="">
					<table width="8%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12">
							<div align="right" class="botonSeleccionar" title="Ver Detalle"
								onclick="javascript:detalleEvento(<bean:write name='evento' property='idEvento'/>)">
								</div>
							</td>
						</tr>
					</table>
					</div>
				</display:column>
			</display:table>
			</div>
			<script type="text/javascript">
			inicializar();
			
			</script>

		</html:form></div>
	</tiles:put>
</tiles:insert>