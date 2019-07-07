package com.pragma.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.seguridad.AuthenticationService;
import com.pragma.bus.api.GenericService;
import com.pragma.util.ContextUtil;

/**
 * Service Generico para hacer los ABM
 * 
 * @author Pragma Consultores
 * @version $Revision: 1.1 $
 */

// public class GenericABMAction<T> }
public abstract class GenericAction extends TemplateABMAction {
	// ~ Instance fields
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private GenericService servicio;

	// ~ Methods
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// private Class<T> type;

	//
	// /**
	// * Crea un objeto <code>GenericABMAction</code>.
	// *
	// * @param type Class
	// */
	// public GenericAction(String service) {
	// this.servicio=
	// (GenericService)WebContextUtil.getBeanFactory().getBean(service);
	// }
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
	 * @return Documentar el valor de retorno!
	 */
	public GenericService getServicio() {
		return servicio;
	}

	protected void dataInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// llamo al getAll del generic service
		List collection = servicio.getAll(servicio.getType());

		// guardo la collection en el request
		request.setAttribute("collection", collection);
	}

	public final ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initCargar(mapping, form, request, response);
		// validateCargar(mapping, form, request, response);
		dataCargar(mapping, form, request, response);

		return forwardCargar(mapping, form, request, response);
	}

	protected void initCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected void dataCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected ActionForward forwardCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
		return mapping.findForward("success");
	}

	protected ActionForward forwardInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
		return mapping.findForward("success");
	}

	protected ActionForward forwardAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
		return mapping.findForward("success");
	}

	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// levanto el id de la entidad a editar
		String id = request.getParameter("id");

		if ((id == null) || (id == "")) {
			throw new RuntimeException("Es necesario pasar el id ");
		}

		Object bean = servicio.load(new Long(id));
		BeanUtils.copyProperties(form, bean);
	}

	protected ActionForward forwardEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
		return mapping.findForward("success");
	}

	protected void dataVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected void dataBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// levanto el id de la entidad a editar
		String id = request.getParameter("id");

		if ((id == null) || (id == "")) {
			throw new RuntimeException("Es necesario pasar el id para eliminar el objeto");
		}

		servicio.delete(servicio.load(new Long(id)));
	}

	protected ActionForward forwardBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// salgo siempre por success
		return mapping.findForward("success");
	}
	
	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
}