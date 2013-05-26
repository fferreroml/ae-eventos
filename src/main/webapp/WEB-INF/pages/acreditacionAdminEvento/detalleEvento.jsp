<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/display" prefix="display"%>



<tiles:insert definition="template.default" flush="true">
	<tiles:put name="titulo" type="string" value="Listado de Solicitudes de Acreditacion" />

	<tiles:put name="body" type="string">
		<script>
			function onAcreditar(id,evento){
				if (confirm("\u00bfCambiar el estado de la Acreditacion?")){
					document.location.href = "AcreditacionAdminEvento.do?method=onCambiarEstado&acreditacion="+id+"&estado=aprobar"+"&evento="+evento;
				}
			}

			function onRechazar(id,evento){
				if (confirm("\u00bfCambiar el estado de la Acreditacion?")){
				document.location.href = "AcreditacionAdminEvento.do?method=onCambiarEstado&acreditacion="+id+"&estado=rechazar"+"&evento=" +evento;
				}
			}

			function onEnviarMail(id,evento){
				document.location.href = "AcreditacionAdminEvento.do?method=onEnviarMail&acreditacion="+id+"&evento="+evento;
			}



			function tomarEvento(id) {
				 document.acreditacionAdminEventoForm.method.value="onListarDetalleEvento";
				 document.acreditacionAdminEventoForm.eventoSeleccionado.value = id;
				 onSubmitStruts('AcreditacionAdminEvento','acreditacionAdminEventoForm', 'onListarDetalleEvento');
				 }

		
		</script>
		<div align="center"><html:form action="/AcreditacionAdminEvento">
			<html:hidden property="method" value="" />
			<html:hidden name="acreditacionAdminEventoForm" property="eventoSeleccionado"/>
			
		<div align="center">
			<table align="left" width="100%">
				<tr>
				<td>Apellido: <br />  <html:text name="acreditacionAdminEventoForm" property="apellidoFiltro"
					styleClass="InputFiltro" maxlength="120"/></td>
					 
				<td>Nombre:  <br />  <html:text name="acreditacionAdminEventoForm" property="nombreFiltro"
					styleClass="InputFiltro"  maxlength="120" /> </td>
					
				<td>E-mail: <br />  <html:text name="acreditacionAdminEventoForm" property="emailFiltro"
					styleClass="InputFiltro"  maxlength="120" /></td>
				<td>Estado:  <br /> 
				<html:select name="acreditacionAdminEventoForm" property="estadoFiltro" style="width:95%">
				 <html:option value="-1">Todos</html:option>
				<html:optionsCollection name="acreditacionAdminEventoForm" property="estados"
				 label="descripcion" value="valor" styleClass="inputFiltro"/>
				  </html:select>
				</td>		
				<td>
				 <br />
				<input value="Filtrar" class="BtnFiltro" type="button" 
				onclick="javascript:tomarEvento(<bean:write name='acreditacionAdminEventoForm' property='eventoSeleccionado'/> )"/>
					
				</td>
				</tr>
			</table>
			
			    <br />
                <br />
                <br />
			
			</div>
			
    			<br />
                <br />
			
		<display:table excludedParams="*"
				requestURI="AcreditacionAdminAction.do?method=onListarSolicitudes" pagesize="20"
				id="usuario" name="acreditacionAdminEventoForm.acreditaciones" id="acreditacion"
				style="width: 100%; border=0;"  cellpadding="0" cellspacing="0" class="list" >
				

				<display:column
					style="width: 18%;height:18px;"  title="Apellido"
					property="asistente.apellido">

				</display:column>

				<display:column  style="width: 18%;height:18px;" title="Nombre" property="asistente.nombre">
				</display:column>

				<display:column  style="width: 22%;height:18px;" title="E-mail" property="asistente.email">
				</display:column>
				
				<display:column 	style="width: 15%;height:18px;" title="Estado" property="estadoAcreditacion.descripcion">
				</display:column>

			<display:column style="width: 7%;height:18px;text-align:center;" title="Acreditar">
					<div align="center" class="">
					<table width="8%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10">
							<logic:equal name="acreditacion" property="estadoAcreditacion.valor" value="0">
							<div align="right" class="botonAcreditar" title="Acreditar la Solicitud"
								onclick="javascript:onAcreditar(<bean:write name='acreditacion' property='idAcreditacion'/>,<bean:write name='acreditacionAdminEventoForm' property='eventoSeleccionado'/>)">
								</div>
								
							</logic:equal>
							<logic:notEqual name="acreditacion" property="estadoAcreditacion.valor" value="0">
							
							<logic:equal name="acreditacion" property="estadoAcreditacion.valor" value="3">
							<div align="right" class="botonAcreditar" title="Acreditar la Solicitud"
								onclick="javascript:onAcreditar(<bean:write name='acreditacion' property='idAcreditacion'/>,<bean:write name='acreditacionAdminEventoForm' property='eventoSeleccionado'/>)">
								</div>	
							</logic:equal>				
							<logic:notEqual name="acreditacion" property="estadoAcreditacion.valor" value="3">
									&nbsp;
									</logic:notEqual>
							</logic:notEqual>
							
							</td>
						</tr>
					</table>
					</div>
				</display:column>
				
				<display:column 
					style="width: 5%;height:18px;text-align:center;" title="Rechazar">
					<div align="center" class="">
					<table width="8%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10">
							<logic:equal name="acreditacion" property="estadoAcreditacion.valor" value="2">
								<div align="right" class="botonRechazar" title="Rechazar la Solicitud"
								onclick="javascript:onRechazar(<bean:write name='acreditacion' property='idAcreditacion'/>,<bean:write name='acreditacionAdminEventoForm' property='eventoSeleccionado'/>)">
								</div>
							</logic:equal>
							
							<logic:notEqual name="acreditacion" property="estadoAcreditacion.valor" value="2">
								
									<logic:equal name="acreditacion" property="estadoAcreditacion.valor" value="3">
									<div align="right" class="botonRechazar" title="Rechazar la Solicitud"
									onclick="javascript:onRechazar(<bean:write name='acreditacion' property='idAcreditacion'/>,<bean:write name='acreditacionAdminEventoForm' property='eventoSeleccionado'/>)">
									</div>
										
									<logic:notEqual name="acreditacion" property="estadoAcreditacion.valor" value="3">
									&nbsp;
									</logic:notEqual>
									</logic:equal>
							   </logic:notEqual>			
							</td>
						</tr>
					</table>
					</div>
				</display:column>
						<display:column 
					style="width: 5%;height:18px;text-align:center;" title="E-Mail">
					<div align="center" class="">
					<table width="8%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="18">
							<logic:equal name="acreditacion" property="estadoAcreditacion.valor" value="2">
								<div align="right" class="botonSeleccionar" title="Enviar E-Mail"
								onclick="javascript:onEnviarMail(<bean:write name='acreditacion' property='idAcreditacion'/>, <bean:write name='acreditacionAdminEventoForm' property='eventoSeleccionado'/>)">
								</div>
								
							</logic:equal>
							<logic:notEqual name="acreditacion" property="estadoAcreditacion.valor" value="2">
							&nbsp;
							</logic:notEqual>
							
							</td>
						</tr>
					</table>
					</div>
				</display:column>
			</display:table>
		</html:form></div>
	</tiles:put>
</tiles:insert>