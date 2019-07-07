package com.fontar.web.action.seguimientos.evaluaciones;

import static com.fontar.data.Constant.ClasificacionEvaluacionSeguimiento.ES_CONTABLE;
import static com.fontar.data.Constant.ClasificacionEvaluacionSeguimiento.ES_TECNICA;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.evaluacion.EvaluarProyectoServicio;
import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.api.workflow.WFEvaluacionServicio;
import com.fontar.util.SessionHelper;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Action base para las tareas de workflow de  
 * <code>Evaluaciones de Seguimiento</code>.<br> 
 * @author ssanchez
 */
public class EvaluacionSeguimientoBaseTaskAction extends GenericJbpmTaskAction {

	protected AdministrarSeguimientoServicio administrarSeguimientoServicio;
	protected WFEvaluacionServicio wfEvaluacionServicio;
	protected EvaluarProyectoServicio evaluarProyectoServicio;
	
	public void setWfEvaluacionServicio(WFEvaluacionServicio wfEvaluacionServicio) {
		this.wfEvaluacionServicio = wfEvaluacionServicio;
	}
	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}
	public void setEvaluarProyectoServicio(EvaluarProyectoServicio evaluarProyectoServicio) {
		this.evaluarProyectoServicio = evaluarProyectoServicio;
	}
	
	/**
	 * Obtiene el <i>idEvaluacion</i> desde el request
	 * y lo mete en la sesión
	 * para que sea usada por las otras solapas.<br>
	 * Si el <i>idEvaluacion</i> no esta en el request
	 * lo busca en la sesión.<br>
	 * @param request
	 * @return idEvaluacion
	 */
	protected Long getIdEvaluacion(HttpServletRequest request){
		Long idEvaluacion = null;
		
		if (validateParameter(request,"id")){
			idEvaluacion = new Long(request.getParameter("id"));
			SessionHelper.setIdEvaluacion(request,idEvaluacion);
		} else {
			idEvaluacion = SessionHelper.getIdEvaluacion(request);
		}
		return idEvaluacion;
	}		
	
	/**
	 * Graba el <i>idEvaluacion</i> en la sesión
	 * que será usado por todos vosotros en
	 * conmemoración nuestra.<br>
	 * @param request
	 * @param idEvaluacion
	 */
	protected void setIdEvaluacion(HttpServletRequest request, Long idEvaluacion){
		SessionHelper.setIdEvaluacion(request,idEvaluacion);
	}
	
	
	/**
	 * Obtiene el <i>idSeguimiento</i> desde el request
	 * y lo mete en la sesión
	 * para que sea usada por las otras solapas.<br>
	 * Si el <i>idSeguimiento</i> no esta en el request
	 * lo busca en la sesión.<br>
	 * @param request
	 * @return idSeguimiento
	 */
	protected Long getIdSeguimiento(HttpServletRequest request){
		Long idSeguimiento = null;
		
		if (validateParameter(request,"id")){
			idSeguimiento = new Long(request.getParameter("id"));
			SessionHelper.setIdSeguimiento(request,idSeguimiento);
		} else {
			idSeguimiento = SessionHelper.getIdSeguimiento(request);
		}
		return idSeguimiento;
	}
	
	/**
	 * Graba el <i>idSeguimiento</i> en la sesión.<br>
	 * @param request
	 * @param idSeguimiento
	 */
	protected void setIdSeguimiento(HttpServletRequest request, Long idSeguimiento){
		SessionHelper.setIdSeguimiento(request,idSeguimiento);
	}
	
	/**
	 * Este seteo se realiza para que el tag <code>actionAuthorize</code>
	 * que se encuentra en el jsp que forwardea este <code>Action</code>
	 * permita o no la visualización del link <i>Editar</i>.<br>
	 * Si el valor de la variable de sesión <i>ACTION_AUTHORIZE</i> es false, 
	 * el link no se muestra.<br>
	 * @param request
	 * @param value
	 */
	protected void setActionAuthorize(HttpServletRequest request, Boolean value) {
		SessionHelper.setActionAuthorize(request,value);
	}
	
	/**
	 * Define si una <code>Evaluación de Seguimiento</code> es
	 * o no de la clase "Técnica" o "Visita Técnica".<br>
	 * @param request
	 * @param value
	 */
	protected void setEsTecnica(HttpServletRequest request, Boolean value) {
		SessionHelper.setVariableValue(request,ES_TECNICA,value);
	}
	
	/**
	 * Define si una <code>Evaluación de Seguimiento</code> es
	 * o no de la clase "Contable" o "Auditoría Contable".<br>
	 * @param request
	 * @param value
	 */
	protected void setEsContable(HttpServletRequest request, Boolean value) {
		SessionHelper.setVariableValue(request,ES_CONTABLE,value);
	}
}
