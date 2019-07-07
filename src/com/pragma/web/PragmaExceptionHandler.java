package com.pragma.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

public class PragmaExceptionHandler extends ExceptionHandler {

	private static final Log LOG = LogFactory.getLog(PragmaExceptionHandler.class);

	private static final String EXCEPTION = "exception";

	private static final String EXCEPTION_CAUSE = "causeException";

	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response) throws ServletException {

		// TODO crear un decorador de la exception para facilitrar la obtencion
		// de datos en el jsp
		if (ex.getCause() != null)
			request.setAttribute(EXCEPTION_CAUSE, ex.getCause());

		request.setAttribute(EXCEPTION, ex);

		return super.execute(ex, ae, mapping, formInstance, request, response);
	}

	protected void logException(Exception e) {
		// Log each stakcItem for e
		for (StackTraceElement element : e.getStackTrace()) {
			LOG.debug(element);
		}
		super.logException(e);
	}

	protected void storeException(HttpServletRequest request, String property, ActionMessage error,
			ActionForward forward, String scope) {
		super.storeException(request, property, error, forward, scope);
	}

}
