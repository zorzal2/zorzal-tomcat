package com.pragma.toolbar.exception;

import java.io.PrintStream;

public class UnControlledException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Exception internalException;
	
	public UnControlledException(String string) {
		super(string);
	}

	protected UnControlledException() {
		super();
	}

	public UnControlledException(Exception ex) {
		super();
		this.internalException = ex;
	}
	
	public void printStackTrace(PrintStream s) {
		if (this.internalException != null) {
			this.internalException.printStackTrace(s);
			s.print("\n\n");
		}
		super.printStackTrace(s);			
	}
	
	public Exception getInternalException() {
		return this.internalException;
	}
}
