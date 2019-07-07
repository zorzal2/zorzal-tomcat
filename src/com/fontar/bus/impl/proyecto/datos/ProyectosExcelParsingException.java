package com.fontar.bus.impl.proyecto.datos;

public class ProyectosExcelParsingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String messageKey;
	private String[] messageParameters;
	
	public ProyectosExcelParsingException(String messageKey, String... parameters) {
		this.messageKey = messageKey;
		this.messageParameters = parameters==null? new String[]{} : parameters;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String[] getMessageParameters() {
		return messageParameters;
	}

	public void setMessageParameters(String[] messageParameters) {
		this.messageParameters = messageParameters;
	}
}
