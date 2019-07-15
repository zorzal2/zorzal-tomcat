function CallLater(func) {
	window.setTimeout(func, 10);
}

function request(action, parameters, target) {
	var requestForm = document.createElement('form');
	document.body.appendChild(requestForm);
	requestForm.method='post';
	requestForm.action=action;
	if(target) requestForm.target=target;
	if(parameters!=null) {
		for(paramName in parameters) {
			var input = document.createElement("INPUT");
	        input.type = "hidden";
    	    input.name = paramName;
	        input.value = parameters[paramName];
	        requestForm.appendChild(input);
		}
	}
	requestForm.submit();
}

//Instancia un template del tipo 'Hola %usuario%!!' con los atributos del objeto dado
//Ej. apply('Hola, %usuario%!', {usuario: 'Pepe'}) devuelve 'Hola, Pepe!'
function apply(template, object) {
	for(prop in object) {
		template = template.replace(new RegExp("%"+prop+"%", 'g'), object[prop]);
	}
	return template;
}

var XMLChars = {
	'<' : '&lt;',
	'>' : '&gt;',
	'"' : '&quot;',
	"'" : '&#039;',
	'&' : '&amp;',
	'\t' : '&#x9;',
	'\n' : '&#xA;'
}
function escapeXML(str){
	if(!str) return str;
	function replacechar(match){
		return XMLChars[match];
	}
	var re=/[<>"'&\n\t]/g
	return (''+str).replace(re, function(m){return replacechar(m)})
}

//Validaciones de tipos de datos
function isNumber(anObject) {
	return ("" + anObject).match(/^-?\d+(\.{1}\d+)?$/)
}
//Validaciones de tipos de datos
function isLong(anObject) {
	return ("" + anObject).match(/^-?\d+$/)
}

function isBoolean(anObject) {
	return anObject==true || anObject==false;
}

function isDate(strDate) {
	//var strDatestyle = "US"; //Estilo Americano mm/dd/aaaa
	var strDatestyle = "EU";  //Estilo europeo dd/mm/aaaa
	var strDateArray;
	var strDay;
	var strMonth;
	var strYear;
	var intday;
	var intMonth;
	var intYear;
	var booFound = false;
	//var strSeparatorArray = new Array("-"," ","/",".");
	var strSeparatorArray = new Array("/");
	var intElementNr;
	var err = 0;
	var strMonthArray = new Array(12);
	strMonthArray[0] = "01";
	strMonthArray[1] = "02";
	strMonthArray[2] = "03";
	strMonthArray[3] = "04";
	strMonthArray[4] = "05";
	strMonthArray[5] = "06";
	strMonthArray[6] = "07";
	strMonthArray[7] = "08";
	strMonthArray[8] = "09";
	strMonthArray[9] = "10";
	strMonthArray[10] = "11";
	strMonthArray[11] = "12";

	if (strDate.length < 1) {
		return true;
	}

	if (strDate.length < 10) {
		return false;
	}
	
	for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
		if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
			strDateArray = strDate.split(strSeparatorArray[intElementNr]);
			if (strDateArray.length != 3) {
				err = 1;
				return false;
			}else {
				strDay = strDateArray[0];
				strMonth = strDateArray[1];
				strYear = strDateArray[2];
			}
		booFound = true;
	   }
	}
	if (booFound == false) {
		if (strDate.length>5) {
			strDay = strDate.substr(0, 2);
			strMonth = strDate.substr(2, 2);
			strYear = strDate.substr(4);
	   }
	}
	
	if (isNaN(strYear)) {
		return false;
	}
	
	if (strYear.length != 4) {
		return false;
	}
	
	if (parseInt(strYear) < 1900) {
		return false;
	}
	
	// Fecha estilo Norteamericano mm/dd/aaaa
	if (strDatestyle == "US") {
		strTemp = strDay;
		strDay = strMonth;
		strMonth = strTemp;
	}
	

	intday = parseInt(strDay, 10);

	if (isNaN(intday)) {
		err = 2;
		return false;
	}
	
	intMonth = parseInt(strMonth, 10);
	if (isNaN(intMonth)) {
		for (i = 0;i<12;i++) {
			if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {
				intMonth = i+1;
				strMonth = strMonthArray[i];
				i = 12;
			}
		}
		if (isNaN(intMonth)) {
			err = 3;
			return false;
	   }
	}
	
	intYear = parseInt(strYear, 10);
	
	if (isNaN(intYear)) {
		err = 4;
		return false;
	}
	
	if (intMonth>12 || intMonth<1) {
		err = 5;
		return false;
	}
	
	if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
		err = 6;
		return false;
	}
	
	if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
		err = 7;
		return false;
	}
	
	if (intMonth == 2) {
		if (intday < 1) {
			err = 8;
			return false;
		}
		if (LeapYear(intYear) == true) {
			if (intday > 29) {
				err = 9;
				return false;
			}
		}else {
			if (intday > 28) {
				err = 10;
				return false;
			}
		}
	}
		return true;
	}
	
	function LeapYear(intYear) {
		if (intYear % 100 == 0) {
			if (intYear % 400 == 0) { return true; }
		}else {
		if ((intYear % 4) == 0) { return true; }
	}
	return false;
}

function getMessage(id) {
	var object = document.getElementById(id);
	return object.text || object.innerHTML;
}

function ShowAlert(id, vars) {
	if(!vars) vars={};
	window.alert(apply(getMessage(id), vars));
}

function formatDate(date) {
	if(!date) return null;

	var d = date.getDate();
	var m = date.getMonth()+1;
	var a = date.getFullYear();

	if(d<10) d='0'+d;
	if(m<10) m='0'+m;
	if(a<10) a='000'+a;
	if(a<100) a='00'+a;
	if(a<1000) a='0'+a;
	
	return ''+d+'/'+m+'/'+a;
}
//Parser tolerante tipo dd/mm/yyyy
function parseDate(strDate) {
	if(strDate==null) return null;
	var regexpRes = strDate.match(/^(\d{1,2})\/(\d{1,2})\/((?:\d{2})|(?:\d{4}))$/);
	if(regexpRes==null) {
		return null;
	}
	var day = parseInt(regexpRes[1],10);
	var month = parseInt(regexpRes[2],10)-1;
	var year = parseInt(regexpRes[3],10);
	if(year<100) {
		//Correccion de a?o corto
		if(year<70) year+=2000;
		else year+= 1900;
	}
	
	var ret = new Date(year, month, day);
	if(ret.getDate()!=day) return null;
	if(ret.getMonth()!=month) return null;
	if(ret.getFullYear()!=year) return null;
	return ret;
}

function parseBoolean(bool) {
	bool = (''+bool).toUpperCase();
	switch(bool) {
		case "1":
		case "TRUE":
		case "SI":
		case "V":
		case "VERDADERO":
			return true;
		case "0":
		case "FALSE":
		case "NO":
		case "F":
		case "FALSO":
			return false;
		default:
			return null;
	}
}

function parseToGMTDate(strDate) {
	var date = parseDate(strDate);
	if(!date) return null;
	var GMTDate = new Date();
	GMTDate.setTime(date.getTime() + date.getTimezoneOffset() * 60 * 1000);
	return GMTDate;
}