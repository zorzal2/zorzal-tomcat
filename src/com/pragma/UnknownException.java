package com.pragma;

public class UnknownException extends PragmaException {

	private static final long serialVersionUID = 1L;

	public UnknownException(Exception e) {
		super(e);
	}

	public UnknownException(String e) {
		super(e);
	}

}
