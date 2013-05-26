// global variables //
var TIMER = 5;
var SPEED = 10;
var WRAPPER = 'body';
var IMG_OUT = 'resources/components/msgNeo/images/b1.gif';
var IMG_OVER = 'resources/components/msgNeo/images/b2.gif';
var APP_TITLE = 'SAP - Sistema de Acreditación de Periodistas';

var USE_STANDART_ALERT = true;

if (!USE_STANDART_ALERT){
	if(document.getElementById) {
		window.alert = function(txt) {
			customAlertNeosur(txt);
		}
	}
}

// calculate the current window width //
function pageWidth() {
	return window.innerWidth != null ? window.innerWidth
			: document.documentElement && document.documentElement.clientWidth ? document.documentElement.clientWidth
					: document.body != null ? document.body.clientWidth : null;
}

// calculate the current window height //
function pageHeight() {
	return window.innerHeight != null ? window.innerHeight
			: document.documentElement && document.documentElement.clientHeight ? document.documentElement.clientHeight
					: document.body != null ? document.body.clientHeight : null;
}

// calculate the current window vertical offset //
function topPosition() {
	return typeof window.pageYOffset != 'undefined' ? window.pageYOffset
			: document.documentElement && document.documentElement.scrollTop ? document.documentElement.scrollTop
					: document.body.scrollTop ? document.body.scrollTop : 0;
}

// calculate the position starting at the left of the window //
function leftPosition() {
	return typeof window.pageXOffset != 'undefined' ? window.pageXOffset
			: document.documentElement && document.documentElement.scrollLeft ? document.documentElement.scrollLeft
					: document.body.scrollLeft ? document.body.scrollLeft : 0;
}

function customAlertNeosur(txt){
	showDialog(APP_TITLE, txt);
}

function addEvent(obj, evType, fn, useCapture) {

	if (obj.addEventListener) {
		obj.addEventListener(evType, fn, useCapture);

	} else if (obj.attachEvent) {
		obj.attachEvent("on" + evType, fn);

	} else {
		obj['on' + evType] = fn;
	}
}

// build/show the dialog box, populate the data and call the fadeDialog function
// //
function showDialog(title, message) {
	var dialog;
	var dialogheader;
	var dialogclose;
	var dialogtitle;
	var dialogcontent;
	var dialogmask;

	dialog = document.createElement('div');
	dialog.id = 'dialog';
	dialogheader = document.createElement('div');
	dialogheader.id = 'dialog-header';
	dialogtitle = document.createElement('div');
	dialogtitle.id = 'dialog-title';
	dialogclose = document.createElement('div');
	dialogclose.id = 'dialog-close';
	dialogcontent = document.createElement('div');
	dialogcontent.id = 'dialog-content';
	dialogmask = document.createElement('div');
	dialogmask.id = 'dialog-mask';
	dialogaccept = document.createElement('div');
	dialogaccept.id = 'dialog-accept';
	dialogtext = document.createElement('div');
	dialogtext.id = 'dialog-text';

	document.body.appendChild(dialogmask);
	document.body.appendChild(dialog);
	dialog.appendChild(dialogheader);
	dialogheader.appendChild(dialogtitle);
	dialogheader.appendChild(dialogclose);
	dialog.appendChild(dialogcontent);
	dialogcontent.appendChild(dialogtext);

	var input = document.createElement("input");
	input.setAttribute("type", "image");
	input.setAttribute("id", "btnAceptar");
	input.setAttribute("src", IMG_OUT);

	addEvent(input, 'click', hideDialog, false);

	addEvent(input, 'mouseover', function(e) {
		var inp = document.getElementById('btnAceptar')		
		inp.src = IMG_OVER;
	}, true);

	addEvent(input, 'mouseout', function(e) {
		var inp = document.getElementById('btnAceptar')		
		inp.src = IMG_OUT;
	}, true);

	dialogaccept.appendChild(input);
	dialogcontent.appendChild(dialogaccept);

	dialogclose.setAttribute('onclick', 'hideDialog()');
	dialogclose.onclick = hideDialog;

	dialog.style.opacity = .00;
	dialog.style.filter = 'alpha(opacity=0)';
	dialog.alpha = 0;

	var width = pageWidth();
	var height = pageHeight();
	var left = leftPosition();
	var top = topPosition();
	var dialogwidth = dialog.offsetWidth;
	var dialogheight = dialog.offsetHeight;
	var topposition = top + (height / 3) - (dialogheight / 2);
	var leftposition = left + (width / 2) - (dialogwidth / 2);
	dialog.style.top = topposition + "px";
	dialog.style.left = leftposition + "px";
	dialogheader.className = "warningheader";
	dialogcontent.className = 'warning';

	dialogtitle.innerHTML = title;
	dialogtext.innerHTML = message.replace(/\n/g, '<br/>');

	var content = document.getElementById(WRAPPER);
	dialogmask.style.height = pageHeight() + 'px';
	dialog.timer = setInterval("fadeDialog(1)", TIMER);

	dialogclose.style.visibility = "visible";

	setTimeout(
			"document.getElementById('btnAceptar').focus(); document.getElementById('btnAceptar').select()",
			10);

}

function changeBtnImage(ev) {
	alert(ev);
}

// hide the dialog box //
function hideDialog() {

	var dialog = document.getElementById('dialog');
	clearInterval(dialog.timer);
	dialog.timer = setInterval("fadeDialog(0)", TIMER);
}

// fade-in the dialog box //
function fadeDialog(flag) {
	if (flag == null) {
		flag = 1;
	}

	var dialog = document.getElementById('dialog');
	var mask = document.getElementById('dialog-mask');
	var value;
	if (flag == 1) {
		value = dialog.alpha + SPEED;
	} else {
		value = dialog.alpha - SPEED;
	}
	dialog.alpha = value;
	dialog.style.opacity = (value / 100);
	dialog.style.filter = 'alpha(opacity=' + value + ')';

	if (value >= 99) {
		clearInterval(dialog.timer);
		dialog.timer = null;
	} else if (value <= 1) {
		clearInterval(dialog.timer);

		dialog.parentNode.removeChild(dialog);
		mask.parentNode.removeChild(mask);

		if (focusControl != null) {
			focusControl.select();
			focusControl.focus();
			focusControl = null;
		}
	}
}