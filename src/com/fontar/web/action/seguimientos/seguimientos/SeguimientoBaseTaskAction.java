package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.util.SessionHelper;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Action base para las tareas de workflow de seguimiento. 
 * @author ssanchez
 */
public class SeguimientoBaseTaskAction extends GenericJbpmTaskAction {

	protected AdministrarSeguimientoServicio administrarSeguimientoServicio;
	protected WFSeguimientoServicio wfSeguimientoServicio;
	protected AdministrarEvaluacionesServicio administrarEvaluacionesServicio;

	public void setAdministrarEvaluacionesServicio(AdministrarEvaluacionesServicio administrarEvaluacionesServicio) {
		this.administrarEvaluacionesServicio = administrarEvaluacionesServicio;
	}
	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}
	public void setWfSeguimientoServicio(WFSeguimientoServicio wfSeguimientoServicio) {
		this.wfSeguimientoServicio = wfSeguimientoServicio;
	}
	
	/**
	 * Graba el <i>idSeguimiento</i> en la sesión.<br>
	 * @param request
	 * @param idSeguimiento
	 */
	protected void setIdSeguimiento(HttpServletRequest request, Long idSeguimiento){
		SessionHelper.setIdSeguimiento(request,idSeguimiento);
	}
}
