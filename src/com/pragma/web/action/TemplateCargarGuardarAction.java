package com.pragma.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.pragma.bus.BusinessException;
import com.pragma.web.InvalidForwardException;

public class TemplateCargarGuardarAction extends BaseMappingDispatchAction {

	/**
	 * Accion de Carga, normalmente se levantan datos de algún servicio particular en el método executeCargar
	 * @param mapping
	 * 
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public final ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// no levanto los errores con getErrors(request) pq puedo venir de una validación fallida
		ActionMessages messages = new ActionMessages();
		

		// grabo un token que chequeo en la terminación y en la ejecución
		saveToken(request);

		validateCargar(mapping, form, request, response, messages);

		// ¿hubo errores en el validate?
		if (messages.isEmpty()) {
			try {
				executeCargar(mapping, form, request, response, messages);
			}
			catch (BusinessException ex) {
				addError(messages, ex);
			}
		}
				
		ActionForward forward = null;
		
		// si hay errores voy a invalid
		if (!messages.isEmpty()) {
			
			saveErrors(request,messages);
			
			if (mapping.getInput() == null) {
				forward = mapping.findForward(FORWARD_INVALID);
			}
			else {
				forward = mapping.getInputForward();
			}
		} else {
			forward = forwardCargar(mapping,form,request,response);
		}		
		
		if (forward == null) {
			throw new InvalidForwardException();
		}
		
		return forward;
	}


	/**
	 * Accion para guardar los datos del formulario, valida que antes se haya ejecutado cargar mediante Tokens
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public final ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Cola de mensajes de error
		ActionMessages messages = this.getErrors(request);
		
		if (checkToken(request)) {
			validateGuardar(mapping, form, request, response, messages);

			// ¿tiene errores?
			if (messages.isEmpty()) {
				try {
					executeGuardar(mapping, form, request, response);
				}
				catch (BusinessException ex) {
					addError(messages, ex);
				}
			}
		}
		else {
			addError(messages, "app.error.abmAction");
		}

		ActionForward forward = null;

		// levanto los errores para ver si tengo que salir por INVALID
		if (!messages.isEmpty()) {
			saveErrors(request, messages);

			// si se de donde vengo, voy!
			if (mapping.getInput() == null) {
				forward = mapping.findForward(FORWARD_INVALID);
			}
			else {
				forward = mapping.getInputForward();
			}

		}
		else {
			forward = forwardGuardar(mapping, form, request, response);
		}

		if (forward == null) {
			throw new InvalidForwardException();
		}
		return forward;
	}

	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages) throws Exception{
	}

	protected ActionForward forwardGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return mapping.findForward(FORWARD_SUCCESS);
	}

	protected void executeGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	}
	
	protected void executeCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages) throws Exception{
	}

	protected void validateCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages) throws Exception{
	}
	
	private ActionForward forwardCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return mapping.findForward(FORWARD_SUCCESS);
	}	
	
	private boolean checkToken(HttpServletRequest request) {
		if (useToken()) {
			return isTokenValid(request, true);
		}
		else {
			return true;
		}
	}
	
	protected boolean useToken(){
		return true;
	}
	
}
