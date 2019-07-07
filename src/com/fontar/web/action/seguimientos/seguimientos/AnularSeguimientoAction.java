package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.bus.BusinessException;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * @author gboaglio  
 */

public class AnularSeguimientoAction extends GenericJbpmTaskAction {

	private WFSeguimientoServicio wfSeguimientoServicio;

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		if(!ActionUtil.isEncryptionContextAvailable()) {
			BusinessException Ex = new BusinessException();
			Ex.setBundleKey("app.error.encrypt");
			throw Ex;
		}
		
		SeguimientoVisualizacionCabeceraDTO dto = wfSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(idTaskInstance);
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, dto);
	 
	}

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		// TODO : GB/ No se permite anular el seguimiento si tiene evaluaciones abiertas		
		// Este código actual corresponde a la validación de anular proyecto. Usarlo como guía.
		
		//		super.validateCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		
		//
		//		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		//		Long idProyecto = taskHelper.getIdProyecto();
		//		ProyectoRaizDAO proyectoRaizDao = (ProyectoRaizDAO)ContextUtil.getBean("proyectoRaizDao");
		//
		//		ProyectoRaizBean proyectoRaiz = proyectoRaizDao.read(new Long(idProyecto));
		//		List<String> evalAbiertas = proyectoRaiz.tieneEvaluacionesAbiertas();		
		//
		//		Iterator i = evalAbiertas.iterator();
		//		while (i.hasNext()) {
		//			addError(messages,"app.proyecto.noAnularAbierta",i.next());
		//		}
		//		
		//		// Chequeo que el proyecto no tenga seguimientos abiertos
		//		List<String> seguimientos = proyectoRaiz.seguimientosAbiertos();
		//
		//		i = seguimientos.iterator();
		//		while (i.hasNext()) {
		//			addError(messages,"app.proyecto.noAnularSeguimientoAbierto",i.next());
		//		}
	}

	
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
			String observaciones = FormUtil.getStringValue(form, "observacion");
			wfSeguimientoServicio.anularSeguimiento(observaciones, idTaskInstance);
	}

	
	public WFSeguimientoServicio getWfSeguimientoServicio() {
		return wfSeguimientoServicio;
	}

	public void setWfSeguimientoServicio(WFSeguimientoServicio wfSeguimientoServicio) {
		this.wfSeguimientoServicio = wfSeguimientoServicio;
	}

}