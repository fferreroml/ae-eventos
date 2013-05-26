<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>

<tiles:insert definition="template.default" flush="true">
	<tiles:put name="body" type="string">
		<tiles:put name="titulo" type="string" value="BIENVENIDOS  " />
		<div align="center"><img align="middle"
			src="resources/images/logo.jpg"  width="223" height="154" /><br />
		</div>
	</tiles:put>
</tiles:insert>