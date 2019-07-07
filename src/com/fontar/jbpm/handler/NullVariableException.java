package com.fontar.jbpm.handler;

import com.pragma.jbpm.WorkFlowException;

@SuppressWarnings("serial")
public class NullVariableException extends WorkFlowException {

	public NullVariableException(String variableName, String processName) {
		super("La variable " + variableName + " es nula en el proceso " + processName);
		// TODO Auto-generated constructor stub
	}

	public NullVariableException(String variableName, String processName, Throwable cause) {
		super("La variable " + variableName + " es nula en el proceso " + processName, cause);
		// TODO Auto-generated constructor stub
	}

}
