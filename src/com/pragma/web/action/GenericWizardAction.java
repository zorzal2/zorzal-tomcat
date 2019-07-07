package com.pragma.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pragma.bus.api.GenericService;

/**
 * Documentar el proposito de la clase!
 * 
 * @author Pragma Consultores
 * @version $Revision: 1.1 $
 */
public class GenericWizardAction extends BaseMappingDispatchAction {
	// ~ Instance fields
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private GenericService servicio;

	// ~ Methods
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public GenericService getServicio() {
		return servicio;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param servicio Documentar el parametro!
	 */
	public void setServicio(GenericService servicio) {
		this.servicio = servicio;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param mapping Documentar el parametro!
	 * @param form Documentar el parametro!
	 * @param request Documentar el parametro!
	 * @param response Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 * 
	 * @throws Exception Documentar la excepcion!
	 */
	public ActionForward doPrevious(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		processPrevious(mapping, form, request, response);

		return mapping.findForward("previous");
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param mapping Documentar el parametro!
	 * @param form Documentar el parametro!
	 * @param request Documentar el parametro!
	 * @param response Documentar el parametro!
	 * 
	 * @throws Exception Documentar la excepcion!
	 */
	protected void processPrevious(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param mapping Documentar el parametro!
	 * @param form Documentar el parametro!
	 * @param request Documentar el parametro!
	 * @param response Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 * 
	 * @throws Exception Documentar la excepcion!
	 */
	public ActionForward doNext(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		processNext(mapping, form, request, response);

		return mapping.findForward("next");
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param mapping Documentar el parametro!
	 * @param form Documentar el parametro!
	 * @param request Documentar el parametro!
	 * @param response Documentar el parametro!
	 * 
	 * @throws Exception Documentar la excepcion!
	 */
	protected void processNext(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param mapping Documentar el parametro!
	 * @param form Documentar el parametro!
	 * @param request Documentar el parametro!
	 * @param response Documentar el parametro!
	 * 
	 * @return Documentar el valor de retorno!
	 * 
	 * @throws Exception Documentar la excepcion!
	 */
	public ActionForward doFinish(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		processFinish(mapping, form, request, response);
		return mapping.findForward("finish");
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param mapping Documentar el parametro!
	 * @param form Documentar el parametro!
	 * @param request Documentar el parametro!
	 * @param response Documentar el parametro!
	 * 
	 * @throws Exception Documentar la excepcion!
	 */
	protected void processFinish(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}
}