package com.pragma.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.bus.BusinessException;
import com.pragma.bus.DeveloperException;
import com.pragma.jbpm.JbpmManagerService;
import com.pragma.util.ContextUtil;
import com.pragma.web.NavigationManager;

/**
 * Action Generico para tareas orquestadas por el JBPM
 * 
 * @author Pragma Consultores
 * @version $Revision: 1.1 $
 */

public abstract class GenericJbpmTaskAction extends BaseMappingDispatchAction {


	
	/**
	 * Template Method que ejecuta la acción al momento de cargar la tarea
	 * indicada en el process instance del JBPM Orden de ejecución de los
	 * métodos abstractos del template: 1. initCargarTarea 2.
	 * validateCargarTarea 3. executeCargarTarea (es más importante, es la
	 * ejecución ) 4. setForwardCargarTarea
	 */
	public final ActionForward cargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// no levanto los errores con getErrors(request) pq puedo venir de una validación fallida
		ActionMessages messages = new ActionMessages();
		String forward = FORWARD_SUCCESS;

		// grabo un token que chequeo en la terminación y en la ejecución
		if(useToken())saveToken(request);

		// levanto el id de la tarea actual
		Long currentTaskInstanceId = (Long) request.getSession().getAttribute(JbpmConstants.WebVariableNames.CURRENT_TASK);

		if (currentTaskInstanceId == null) {
			addError(messages,"app.wfl.error.cargarTarea");
		}
		else {
			initCargarTarea(mapping, form, request, response, messages, currentTaskInstanceId);
			validateCargarTarea(mapping, form, request, response, messages, currentTaskInstanceId);

			// ¿hubo errores en el validate?
			if (messages.isEmpty()) {
				try {
					executeCargarTarea(mapping, form, request, response, messages, currentTaskInstanceId);
				}
				catch (BusinessException ex) {
					addError(messages, ex);
				}
			}
		}
		
		// si hay errores voy a invalid
		if (!messages.isEmpty()) {
			
			saveErrors(request,messages);
			
			// ¿a donde vuelvo ante un error? la pila me lo dice
			ActionForward previous = NavigationManager.getPreviousAction(request);
		
			// si la pila no sabe me vuelvo a la bandeja de entrada
			if (previous == null){
				return mapping.findForward(FORWARD_BANDEJA_ENTRADA);
			} else {
				return previous;
			}
		} else {
			// todo OK! me voy a cargar la tarea
			forward = setForwardCargarTarea(mapping, form, request, response, messages, currentTaskInstanceId);
			return mapping.findForward(forward);	
		}		
	}

	/**
	 * init de la acción
	 */
	protected void initCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

	}

	/**
	 * Ejecuta una validación sobre el formulario
	 */
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
	}

	/**
	 * Ejecuta la tarea propiamente dicha, pueden hacerse validaciones aqui
	 * también Lo que quita importancia al abstract validate. Puede que quede
	 * deprecado
	 */
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
	}

	/**
	 * Selecciona el name del forward en caso de éxito
	 */
	protected String setForwardCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		return FORWARD_SUCCESS;
	}

	/**
	 * Template Method para la terminación de la tarea cargada previamente, no
	 * se considera el caso que la tarea no se haya ejecutado queda a cargo del
	 * programador mostrar esta tarea en el momento adecuado
	 * 
	 * 1. initEjecutarYTerminarTarea 2. validateEjecutarYTerminarTarea 3.
	 * executeEjecutarYTerminarTarea (es más importante, es la ejecución ) 4.
	 * setForwardEjecutarYTerminarTarea
	 */
	public final ActionForward ejecutarYTerminarTarea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionMessages messages = getErrors(request);

		if (!useToken() || isTokenValid(request)) {

			if(useToken())resetToken(request);

			// levanto el id de la tarea actual
			Long currentTaskInstanceId = (Long) request.getSession().getAttribute(JbpmConstants.WebVariableNames.CURRENT_TASK);

			if (currentTaskInstanceId == null) {
				addError(messages, "app.wfl.error.cargarTarea");
			}
			else {
				initEjecutarYTerminarTarea(mapping, form, request, response, messages, currentTaskInstanceId);
				validateEjecutarYTerminarTarea(mapping, form, request, response, messages, currentTaskInstanceId);

				// ¿hubo errores en el validate?
				if (messages.isEmpty()) {
					try {
						JbpmManagerService service =  (JbpmManagerService) ContextUtil.getBean("jbpmManagerService");
						service.executeEjecutarYTerminarTarea(this,mapping, form, request, response, messages, currentTaskInstanceId);
					}
					catch (BusinessException ex) {
						addError(messages, ex);
					} catch (DeveloperException devEx){
						addError(messages, devEx);
					}
				}
				/*
				 * // Si hay rollback, me fijo quien lo causo
				 * catch(TransactionException ex) { Throwable who = ex;
				 *  // busco si se origino while(who.getCause() != null) { who =
				 * who.getCause(); } //messages.add(ActionErrors.GLOBAL_MESSAGE,
				 * new ActionMessage(who.getMessage()));
				 * addError(messages,who.getMessage());
				 * 
				 * Log log = LogFactory.getLog(this.getClass().getName());
				 * log.error(this,ex); }
				 */
			}
		}
		else {
			addError(messages, "app.wfl.error.cargarTarea");
		}

		// si hay errores voy a invalid
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			// si se de donde vengo, voy!
			if (mapping.getInput() == null) {
				return mapping.findForward(FORWARD_INVALID);
			}
			else {
				return mapping.getInputForward();
			}
		} else {

			// a donde voy? me lo dice la pila de navegación, puede ser un inventario o la bandeja de entrada.
			ActionForward previous = NavigationManager.getPreviousAction(request , NAVIGATION_OVERRIDE_FORWARD);
			
			// si no me dicen, vuelvo a la bandeja
			if (previous == null){
				return mapping.findForward(FORWARD_BANDEJA_ENTRADA);
			} else {
				return previous;
			}
		}
	}

	/**
	 * init de la acción
	 */
	protected void initEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
	}

	/**
	 * Ejecuta una validación sobre el formulario
	 */
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
	}

	/**
	 * Ejecuta la tarea propiamente dicha, pueden hacerse validaciones aqui
	 * también Lo que quita importancia al abstract validate. Puede que quede
	 * deprecado
	 */
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
	}
	protected boolean useToken() {
		return true;
	}
	
	public void ejecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		this.executeEjecutarYTerminarTarea(mapping, form, request, response, messages, idTaskInstance);
	}
}
	