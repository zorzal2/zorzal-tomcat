package com.fontar.bus.impl.seguimientos.seguimientos;

public class RendicionesException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String messageKey;
	private String[] messageParameters;
	
	public RendicionesException(String messageKey, String... parameters) {
		this.messageKey = messageKey;
		this.messageParameters = parameters;
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
