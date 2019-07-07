package com.fontar.web.action.administracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

public class PaginaErrorAction extends MappingDispatchAction {

	public final ActionForward enPaquete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Aca seteo el mensaje de error
		return mapping.findForward("success");
	}
}
