package com.fontar.web.action.administracion.paquete;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.PaqueteCabeceraAssembler;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.dto.PaqueteCabeceraDTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.util.Util;
import com.pragma.util.FormUtil;

public class ConfirmarPaqueteAction extends PaqueteBaseTaskAction {

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		super.validateCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		
		if (messages.isEmpty()) {
			List<String> proySinEval = wfPaqueteServicio.obtenerProyectoPaqueteSinEval(idTaskInstance);
			
			Iterator i = proySinEval.iterator();
			while (i.hasNext()) {
				addError(messages,"app.paquete.noConfirmarProySinEval",i.next());
			}
		}
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		
		PaqueteCabeceraDTO cabeceraDTO = (PaqueteCabeceraDTO)wfPaqueteServicio.getPaqueteDTO(idTaskInstance,new PaqueteCabeceraAssembler());
		request.setAttribute(CabeceraAttribute.PAQUETE, cabeceraDTO);
//		request.setAttribute("tipoPaquete", cabeceraDTO.getTipoPaquete());
	}
	
	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		String codigoActa = FormUtil.getStringValue(form, "codigoActa");
		if(Util.isBlank(codigoActa)) {
			PaqueteDTO paqueteDto = wfPaqueteServicio.obtenerPaquete(idTaskInstance);
			if(paqueteDto.getTipoPaquete().equals(TipoPaquete.DIRECTORIO)) {
				addError(messages, "app.paquete.requiereResolucion");
			} else
				addError(messages, "app.paquete.requiereNroActa");
		}
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		String idPaquete = FormUtil.getStringValue(form, "id");
		String userName = request.getUserPrincipal().getName();
		String codigoActa = FormUtil.getStringValue(form, "codigoActa");
		String observacion = FormUtil.getStringValue(form, "observacion");

		wfPaqueteServicio.confirmarEvaluacion(new Long(idPaquete), userName, idTaskInstance, codigoActa, observacion);
	}

}
