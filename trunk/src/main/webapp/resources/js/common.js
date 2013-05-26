function onSubmitStruts(action, form, go) {
	var htmlForm = document.forms[form];
	htmlForm.action.value = action;
	htmlForm.method.value = go;
	htmlForm.submit();
}

function onSubmitStrutsValidate(action, form, go) {	
	var htmlForm = document.forms[form];
	htmlForm.action.value = action;
	htmlForm.method.value = go;	
	if (eval(htmlForm.onsubmit())) {		
		htmlForm.submit();		
	} else {
		setFocusControl(focusControl);
	}
}


// Validaciones Comunes
/*******************************************************************************
 * isEmpty(value)
 * 
 * Verifica que el valor ingresado no este vacio
 ******************************************************************************/
function isEmpty(valor) {
	if (valor == '') {
		return true;
	}
	return false;
}


/*******************************************************************************
 * isValidRUT(value)
 * 
 * Verifica que el valor ingresado sea un RUT válido
 ******************************************************************************/
function isValidRUT(rut, verificador) {
	
	if (!isInteger(rut)){
		return false;
	}
	
	var rutV = dv(rut); 
	rutV +="";
	
	if (rutV.toLowerCase()	 == verificador.toLowerCase()){
		return true;
	} else {
		return false;
	}
}

function dv(T){
	var M=0,S=1;
	for(;T;T=Math.floor(T/10))
		S=(S+T%10*(9-M++%6))%11;
	return S?S-1:'k';
}


/**********************************************
*validEmail
*
*	Verifica que la direccion de mail
*   este correctamente formada.
**********************************************/
function isValidEmail(_mail){
	var ret = true;
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9])+$/;
	if (!filter.test(_mail)){
		ret = false;
	}
	return ret;
}

/*****************************************************
*isValidAlphanumeric(value)
*
*	Verifica que el valor ingresado sea un string
*   conteniendo solo letras, nros. y espacios en blanco.
*****************************************************/
function isValidAlphanumeric(valor){
	var RegExPattern = /[^a-zA-ZáéíóúñÁÉÍÓÚÑüÜº.=,\-()/&:?¿!¡%\[\]\s0-9]/;
	if (!(valor.match(RegExPattern))) {
		return true;
	}
	return false;
}

/*****************************************************
*isValidCode(value)
*
*	Verifica que el codigo de descarga
*   contenga solo letras, nros.
*****************************************************/
function isValidCode(valor){
	var RegExPattern = /[^a-zA-Z0-9]/;
	if (!(valor.match(RegExPattern))) {
		if (valor.length == 10) {
			return true;
		}
	}
	return false;
}

/*******************************************************************************
 * isInteger(value) (Numeric)
 * 
 * Verifica que el valor ingresado sea un entero
 ******************************************************************************/
function isInteger(valor) {
	var r = new RegExp("^[0-9]*$");
	return (r.test(valor));
}

function setFocusControl(ctrol){
	ctrol.select();
	ctrol.focus();
}

function textEnterClickBtnValidation(e, action, form, go){
	var keynum	

	if(window.event) { // IE
		keynum = e.keyCode
	} else if(e.which) { // Netscape/Firefox/Opera
		keynum = e.which
	}

	if (keynum == 13){ //ENTER		
		onSubmitStrutsValidate(action, form, go);
	}
}
/**********************************************
*centrarWindow
*
*	Centra una ventana
**********************************************/
function centrarWindow(theURL, winName, features, myWidth, myHeight, isCenter) { //v3.0
	if(window.screen)if(isCenter)if(isCenter=="true"){
		var myLeft = (screen.width-myWidth)/2;
		var myTop = (screen.height-myHeight)/2;
		features+=(features!='')?',':'';
		features+='left='+myLeft+',top='+myTop;
	}
	winName = winName.replace(/ /g, "");
	window.open(theURL,winName,features+((features!='')?',':'')+'width='+myWidth+',height='+myHeight);
}


/**********************************************
*checkDate
*
*	Verifica que la fecha sea valida
**********************************************/
function checkDate(d, m, a)
{
	var fecha = new Date(a, m-1, d);

	//alert(fecha.getDate()  + "-" + fecha.getMonth() + "-" + fecha.getFullYear());
	if (fecha.getDate() == d && fecha.getMonth() == m-1 && fecha.getFullYear() == a)
		return true;
	else
		return false;
}

/*****************************************************
*checkTextDate(date,sep,pattern)
* patterns : dd<sep>mm<sep>yyyy y mm<sep>dd<sep>yyyy
*	Verifica que la fecha sea valida
*****************************************************/
function isValidFecha(date,sep,pattern){
	var first,tmp,second,third;
	first = date.substring(0,date.indexOf(sep));
	tmp = date.substring(date.indexOf(sep) + 1,date.length);
	second = tmp.substring(0,tmp.indexOf(sep));
	third = tmp.substring(tmp.indexOf(sep) + 1,tmp.length);
	var d, m, y;
	if(pattern==("dd" + sep + "mm" + sep + "yyyy")){
		d = first;
		m = second;
		y = third;
	}else{
		if(pattern==("mm" + sep + "dd" + sep + "yyyy")){
			m = first;
			d = second;
			y = third;
		}
	}
	return checkDate(d,m,y);
}
