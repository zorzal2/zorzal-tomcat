package com.fontar.web.action.administracion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.api.workflow.WFPaqueteServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Acciòn para la administraciòn de paquetes en el sistema
 * @author ssanchez
 */

public class PaqueteAction extends BaseMappingDispatchAction {

	private WFPaqueteServicio wfPaqueteServicio;

	public void setWfPaqueteServicio(WFPaqueteServicio wfPaqueteServicio) {
		this.wfPaqueteServicio = wfPaqueteServicio;
	}

	/**
	 * PaqueteAction
	 */
	// TODO: Cambiar el nombre del método
	public ActionForward agregarPaquete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = getErrors(request);

		ActionUtil.checkValidEncryptionContext(messages);
		
		if (messages.isEmpty() ){
			
			if(isTokenValid(request)) {
				resetToken(request);
		
				// obtengo los valores del formulario
				String instrumentoFiltrado = BeanUtils.getProperty(form, "instrumentoFiltrado");
				String tratamientoFiltrado = BeanUtils.getProperty(form, "tratamientoFiltrado");
		
				String[] proyectoArray = ((DynaActionForm) form).getStrings("proyectoArray");
		
				Long idInstrumento = FormUtil.getLongValue(form, "instrumentoFiltrado");
				String tratamiento = FormUtil.getStringValue(form, "tratamientoFiltrado");
				String tipoPaquete = FormUtil.getStringValue(form, "tipoPaquete");
				request.setAttribute("tipoPaquete", tipoPaquete);
				
				if (instrumentoFiltrado.equals("")) {
					addError(messages, "app.paquete.requiereInstrumento");
				}
				else if (tratamientoFiltrado.equals("")) {
					addError(messages, "app.paquete.requiereTratamiento");
				}
				else if (proyectoArray.length <= 0) {
					addError(messages, "app.paquete.requiereUnProyecto");
				}
				else {
					wfPaqueteServicio.armarPaquete(proyectoArray, idInstrumento, tratamiento, tipoPaquete);
				}
			}
			else {
				addError(messages, "app.error.abmAction");
			}
		}
		
		// ¿hay errores?
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			if (mapping.getInput() != null) {
				return mapping.getInputForward();
			}
			else {
				return mapping.findForward("invalid");
			}
		}
		else {
			return mapping.findForward("success");
		}
	}

	/**
	 * Muestra la pantalla inicial para armar paquetes
	 */
	public ActionForward armarPaquete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		setCollections(request);
		// seteo el tipo de paquete que se quiere generar
		String tipoPaquete = request.getParameter("tipoPaquete");
		request.setAttribute("tipoPaquete", tipoPaquete);
		// obtengo el listado de proyecto segun los filtros instrumento y tratamiento
		String instrumentoFiltrado = FormUtil.getStringValue(form, "instrumentoFiltrado");
		String tratamientoFiltrado = FormUtil.getStringValue(form, "tratamientoFiltrado");

		List proyectosList = null;

		if (instrumentoFiltrado != null && tratamientoFiltrado != null) {
			// cargo la lista de proyectos
			proyectosList = wfPaqueteServicio.obtenerProyectos(new Long(instrumentoFiltrado), tratamientoFiltrado, tipoPaquete);

			// usados para validar que los filtros se hayan aplicados
			((DynaActionForm) form).set("instrumento", instrumentoFiltrado);
			((DynaActionForm) form).set("tratamiento", tratamientoFiltrado);

			// guardo la collection en el request
			request.setAttribute("proyectosList", proyectosList);
		}

		return mapping.findForward("success");
	}

	/**
	 * Muestra los proyectos que cumplen con los filtros de instrumento y
	 * tratamiento
	 */
	public ActionForward mostrarProyectos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		form.validate(mapping, request);

		// Cola de mensajes de error
		ActionMessages messages = getErrors(request);

		String tipoPaquete = null;
		List proyectosList = null;
		setCollections(request);
		
		//requiere contexto de encriptacion
		ActionUtil.checkValidEncryptionContext(messages);

		// seteo el tipo de paquete que se quiere generar
		tipoPaquete = request.getParameter("tipoPaquete");

		// obtengo el listado de proyecto segun los filtros instrumento y tratamiento
		String instrumento = FormUtil.getStringValue(form, "instrumento");
		String tratamiento = FormUtil.getStringValue(form, "tratamiento");

		if (instrumento == null || instrumento.equals("")) {
			addError(messages, "app.paquete.requiereInstrumento");
		}
		else if (tratamiento == null || tratamiento.equals("")) {
			addError(messages, "app.paquete.requiereTratamiento");
		}
		// ¿hay errores?
		if (!messages.isEmpty()) {
			saveErrors(request, messages);

			if (mapping.getInput() != null) {
				return mapping.getInputForward();
			}
			else {
				return mapping.findForward("invalid");
			}
		}
		else {
			proyectosList = wfPaqueteServicio.obtenerProyectos(new Long(instrumento), tratamiento, tipoPaquete);

			// usados para validar que los filtros se hayan aplicados
			((DynaActionForm) form).set("instrumentoFiltrado", instrumento);
			((DynaActionForm) form).set("tratamientoFiltrado", tratamiento);

			// guardo la collection en el request
			request.setAttribute("proyectosList", proyectosList);
			request.setAttribute("tipoPaquete", tipoPaquete);

			return mapping.findForward("success");
		}
	}

	/**
	 * LLeno los combos para agregar y editar
	 * @param request: usado para setear collections
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		String tipoPaquete;
		if (request.getParameter("tipoPaquete") != null && !request.getParameter("tipoPaquete").equals("")) {
			tipoPaquete = request.getParameter("tipoPaquete");
		}
		else {
			tipoPaquete = request.getAttribute("tipoPaquete").toString();
		}

		InstrumentoDAO instrumentoDAO = (InstrumentoDAO) WebContextUtil.getBeanFactory().getBean("instrumentoDao");
		Collection instrumentoList = new ArrayList();
		if (tipoPaquete.equals(TipoPaquete.COMISION.getName())) {
			instrumentoList.addAll(collectionHandler.getInstrumentoComision(instrumentoDAO));
		}
		else if (tipoPaquete.equals(TipoPaquete.SECRETARIA.getName())) {
			instrumentoList.addAll(collectionHandler.getInstrumentoSecretaria(instrumentoDAO));
		}
		else {
			instrumentoList.addAll(collectionHandler.getInstrumentoDirectorio(instrumentoDAO));
		}

		Collection tratamientoList = new ArrayList();
		tratamientoList.addAll(collectionHandler.getTratamientosPaquete(TratamientoPaquete.class, tipoPaquete));

		request.setAttribute("instrumentos", instrumentoList);
		request.setAttribute("tratamientos", tratamientoList);
	}
	
}