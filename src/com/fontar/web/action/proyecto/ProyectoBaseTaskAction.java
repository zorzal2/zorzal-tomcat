package com.fontar.web.action.proyecto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Clase base para las tareas de proyecto
 * @author fferrara
 * 
 */
public class ProyectoBaseTaskAction extends GenericJbpmTaskAction {

	protected WFProyectoServicio wfProyectoServicio;
	protected AdministrarEvaluacionesServicio administrarEvaluacionesServicio;
	protected AdministrarProyectoServicio administrarProyectoServicio;
	
	public WFProyectoServicio getWfProyectoServicio() {
		return wfProyectoServicio;
	}

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}

	public AdministrarEvaluacionesServicio getAdministrarEvaluacionesServicio() {
		return administrarEvaluacionesServicio;
	}
	
	public void setAdministrarEvaluacionesServicio(AdministrarEvaluacionesServicio administrarEvaluacionesServicio) {
		this.administrarEvaluacionesServicio = administrarEvaluacionesServicio; 
	}
	
	public AdministrarProyectoServicio getAdministrarProyectoServicio() {
		return administrarProyectoServicio;
	}

	public void setAdministrarProyectoServicio(AdministrarProyectoServicio administrarProyectoServicio) {
		this.administrarProyectoServicio = administrarProyectoServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ProyectoDTO proyecto = wfProyectoServicio.obtenerProyecto(idTaskInstance);
		BeanUtils.setProperty(form, "idProyecto", proyecto.getId());

		request.setAttribute("txtProyecto", proyecto.getCodigo());
		request.setAttribute("txtEstado", proyecto.getEstado());
	}
}
