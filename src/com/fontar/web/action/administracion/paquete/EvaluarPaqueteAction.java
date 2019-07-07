package com.fontar.web.action.administracion.paquete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.PaqueteCabeceraAssembler;
import com.pragma.util.FormUtil;

public class EvaluarPaqueteAction extends PaqueteBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		request.setAttribute(CabeceraAttribute.PAQUETE, wfPaqueteServicio.getPaqueteDTO(idTaskInstance,new PaqueteCabeceraAssembler()));
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @author gboaglio, ssanchez
	 * @version 1.01, 29/11/06
	 */
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		String idPaquete = FormUtil.getStringValue(form, "id");
		String codigoActa = BeanUtils.getProperty(form, "codigoActa");
		String observacion = BeanUtils.getProperty(form, "observacion");
		
		wfPaqueteServicio.cargarEvaluacion(new Long(idPaquete), codigoActa, observacion, idTaskInstance);
	}
}