<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Acreditaciones</title>

<script type="text/javascript" src="resources/js/common.js"
	language="javascript"></script>
<script type="text/javascript"
	src="resources/components/msgNeo/dialogNeo.js" language="javascript"></script>

<script type="text/javascript" src="resources/js/jquery.js"
	language="javascript"></script>
<link href="resources/styles/style.css" rel="stylesheet" type="text/css" />

<link href="resources/components/msgNeo/dialogNeo.css" rel="stylesheet"
	type="text/css" />

<script type="text/javascript" src="resources/js/jquery-upload.js"
	language="javascript"></script>


</head>
<body>
<tiles:insert attribute="header" />
<tiles:insert attribute="menu" />
<div class="WrapperContent">
<div class="Content">
<div class="ContentTop"><h1> <tiles:getAsString name="titulo" /></h1> 
 <tiles:insert attribute="body" /></div>
   <div class="ContentFooter"/>
</div>
</div>
</body>
</html:html>
