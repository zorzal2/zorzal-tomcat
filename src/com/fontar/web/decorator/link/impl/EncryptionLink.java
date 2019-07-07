package com.fontar.web.decorator.link.impl;

import java.io.UnsupportedEncodingException;

import com.fontar.web.decorator.link.impl.base.BaseFontarRestrictedLink;

public class EncryptionLink extends BaseFontarRestrictedLink {

	private boolean encryptionAvailable;
	
	
	
	public EncryptionLink(boolean encryptionAvailable) {
		super("javascript:popUpLogon()", "Contrase�a de encriptaci�n");
		this.encryptionAvailable = encryptionAvailable;
		this.setSimplePermissionsRequired("ENCRIPTACION");
	}



	@Override
	protected String displayValueImpl() throws UnsupportedEncodingException {
		StringBuffer tag  = new StringBuffer();
		if(!this.encryptionAvailable){
			tag.append("<img class=\"imageButton\" src=\""); 
			tag.append("images/candado.gif\" onclick=\"");
			tag.append("javascript:popUpLogon()\" alt=\"");
			tag.append("Ingresar Contrase�a de Encriptaci�n\"/>");
		}else{
			tag.append("<img src=\""); 
			tag.append("images/candadoAbierto.gif\" onclick=\"");
			tag.append("javascript:popUpLogout()\" alt=\"");
			tag.append("Salir de zona de Encriptaci�n\"/>");
		}
		return tag.toString();
	}

}
