package com.fontar.web.action.seguimientos.controlAdquisiciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.bus.api.workflow.WFProcedimientoServicio;
import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.jbpm.manager.ProcedimientoTaskInstanceManager;
import com.fontar.util.SessionHelper;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Action Base para las acciones genéricas de procedimientos.<br> 
 * @author ssanchez
 */
public class ProcedimientoBaseTaskAction extends GenericJbpmTaskAction {

	protected static String PROCEDIMIENTO = "procedimiento";
	
	protected AdministrarProcedimientoServicio administrarProcedimientoServicio;
	protected WFProcedimientoServicio wfProcedimientoServicio;
	
	public void setAdministrarProcedimientoServicio(AdministrarProcedimientoServicio administrarProcedimientoServicio) {
		this.administrarProcedimientoServicio = administrarProcedimientoServicio;
	}
	public void setWfProcedimientoServicio(WFProcedimientoServicio wfProcedimientoServicio) {
		this.wfProcedimientoServicio = wfProcedimientoServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		SessionHelper.setIdProcedimiento(request,taskHelper.getIdProcedimiento());
		
		cargarCabeceraProcedimiento(request,idTaskInstance);
		
		String forward = obtenerForward(request);
		SessionHelper.setForwardTiles(request,forward);
	}
	
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		wfProcedimientoServicio.finalizarTarea(idTaskInstance);
	}	
	
	protected Long obtenerIdProcedimiento(HttpServletRequest request){
		
		Long idProcedimiento= null;
		
		if (validateParameter(request,"id")){
			idProcedimiento = new Long(request.getParameter("id"));
			SessionHelper.setIdProcedimiento(request,idProcedimiento);
		} else {
			idProcedimiento = SessionHelper.getIdProcedimiento(request);
		}

		return idProcedimiento;
	}	

	protected ProcedimientoBean cargarCabeceraProcedimiento(HttpServletRequest request, Long idTaskInstance) {

		ProcedimientoBean procedimiento = wfProcedimientoServicio.obtenerProcedimiento(idTaskInstance);
		request.setAttribute(PROCEDIMIENTO, procedimiento);	
		
		return procedimiento;
	}
	
	protected String obtenerForward(HttpServletRequest request) {
		
		String forward = request.getServletPath();
		Integer indice = forward.indexOf(".do");
		forward = forward.substring(1,indice);

		return forward;
	}
}