package com.pragma.util.exception;

import com.pragma.toolbar.exception.UnControlledException;

public class IllegalStateException extends UnControlledException {

	private static final long serialVersionUID = 1L;
	
	public IllegalStateException(String string) {
		super(string);
	}
	public IllegalStateException() {
		super();
	}
}
