<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>

<tiles:insert definition="template.default" flush="true">
	<tiles:put name="body" type="string">
		<tiles:put name="titulo" type="string" value="Error en Envio de Mails" />
		<div align="center">

		<table width="100" border="0" align="center" cellpadding="4"
			cellspacing="0">
			<tr>
				<td>
				<div align="left"  class="Advertent">ERROR ENV&Iacute;O NOTIFICACIONES<br />
				<p>Ha ocurrido un error en el env&iacute;o del mail. Por Favor pongase en contacto con el Administrador del Sitio.</p><span class="subtitle"></span>
				</div>
				</td>
			</tr>
		</table>
		<br />


		</div>
	</tiles:put>
</tiles:insert>