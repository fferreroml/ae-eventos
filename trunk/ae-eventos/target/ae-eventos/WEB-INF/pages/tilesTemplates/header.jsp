<%@ taglib uri="/struts-html" prefix="html"%>
<%@ taglib uri="/struts-bean" prefix="bean"%>
<%@ taglib uri="/struts-logic" prefix="logic"%>
<%@ taglib uri="/struts-tiles" prefix="tiles"%>

<link rel="stylesheet" href="resources/styles/styles.css" type="text/css" />
<div class="FullPage">
     <div class="Header">
            <div class="Logo">
            </div>
            <div class="User">
                  <div class="Name">
                  <div class="img"></div>
                  Usuario: 
                  <bean:write name="AcreditacionesUser" property="nombre" scope="session" />&nbsp;<bean:write
					name="AcreditacionesUser" property="apellido" scope="session" />
				
					</div>
			    <div class="Close" onclick="javascript:go('Home.do?method=init');"> </div>
					
                  </div>
            </div>
     </div>