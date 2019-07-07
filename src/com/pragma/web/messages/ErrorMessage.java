package com.pragma.web.messages;

import org.apache.struts.action.ActionMessage;

public class ErrorMessage extends ActionMessage {
	private static final long serialVersionUID = 1L;

	public ErrorMessage(String arg0) {
		super(arg0);
	}

	public ErrorMessage(String arg0, Object arg1) {
		super(arg0, arg1);
	}

	public ErrorMessage(String arg0, Object arg1, Object arg2) {
		super(arg0, arg1, arg2);
	}

	public ErrorMessage(String arg0, Object arg1, Object arg2, Object arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ErrorMessage(String arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
	}

	public ErrorMessage(String arg0, Object[] arg1) {
		super(arg0, arg1);
	}

	public ErrorMessage(String arg0, boolean arg1) {
		super(arg0, arg1);
	}
}