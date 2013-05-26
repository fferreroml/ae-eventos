<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>

<tiles:insert definition="template.default" flush="true">
	<tiles:put name="body" type="string">
		<tiles:put name="titulo" type="string" value="Generacion de Datamatrix y Envio de Mails Existoso" />
		<div align="center">

		<table width="100%" border="0" align="center" cellpadding="4"
			cellspacing="0">
			<tr>
				<td>
				<div align="left" class="Success">PROCESO EXITOSO<br />
				<p>Se han enviado las notificaciones con exito con exito, verifique el log del proceso para obtener informacion
				mas detallada
 </p><span class="subtitle"></span>
				</div>
				</td>
			</tr>
		</table>
		<br />


		</div>
	</tiles:put>
</tiles:insert>