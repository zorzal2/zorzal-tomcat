package com.pragma.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.pragma.bus.BusinessException;
import com.pragma.web.InvalidForwardException;

public abstract class TemplateABMAction extends BaseMappingDispatchAction {

	public final ActionForward inventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = getErrors(request);

		initInventario(mapping, form, request, response);
		validateInventario(mapping, form, request, response, messages);
		dataInventario(mapping, form, request, response);

		return forwardInventario(mapping, form, request, response);
	}

	protected void initInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected void validateInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
	}

	protected void dataInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected ActionForward forwardInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public final ActionForward agregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (useToken())
			saveToken(request);

		ActionMessages messages = getErrors(request);

		initAgregar(mapping, form, request, response);
		validateAgregar(mapping, form, request, response, messages);
		dataAgregar(mapping, form, request, response);

		return forwardAgregar(mapping, form, request, response);
	}

	protected void initAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected void validateAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
	}

	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected ActionForward forwardAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public final ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (useToken())
			saveToken(request);

		initEditar(mapping, form, request, response);
		validateEditar(mapping, form, request, response);
		dataEditar(mapping, form, request, response);

		return forwardEditar(mapping, form, request, response);
	}

	protected void initEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected void validateEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected abstract void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	protected ActionForward forwardEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public final ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = getErrors(request);

		initVisualizar(mapping, form, request, response);
		validateVisualizar(mapping, form, request, response, messages);
		dataVisualizar(mapping, form, request, response);

		return forwardVisualizar(mapping, form, request, response);
	}

	protected void initVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected void validateVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
	}

	protected abstract void dataVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	protected ActionForward forwardVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
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

	public final ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Cola de mensajes de error
		ActionMessages messages = this.getErrors(request);

		if (checkToken(request)) {
			initGuardar(mapping, form, request, response);
			validateGuardar(mapping, form, request, response, messages);

			// ¿tiene errores?
			if (messages.isEmpty()) {
				try {
					dataGuardar(mapping, form, request, response);
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
				forward = mapping.findForward("invalid");
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

	protected boolean useToken() {
		return true;
	}

	protected void initGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
	}

	protected abstract void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	protected ActionForward forwardGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public final ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Cola de mensajes de error
		ActionMessages messages = getErrors(request);

		initBorrar(mapping, form, request, response);
		validateBorrar(mapping, form, request, response, messages);
		
		// ¿tiene errores?
		if (messages.isEmpty()) {
			try {
				dataBorrar(mapping, form, request, response);
			}
			catch (BusinessException ex) {
				addError(messages, ex);
			}
		}
		
		ActionForward forward = null;
		
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
			forward = forwardBorrar(mapping, form, request, response);
		}
		
		if (forward == null) {
			throw new InvalidForwardException();
		}
		return forward;
	}

	protected void initBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected void validateBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
	}

	protected abstract void dataBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	protected ActionForward forwardBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
		return mapping.findForward(FORWARD_SUCCESS);
	}
}