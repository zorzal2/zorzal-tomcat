package com.fontar.web.action.seguimientos.controlAdquisiciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento;
import com.fontar.jbpm.manager.ProcedimientoTaskInstanceManager;

/**
 * Action para cargar la fundamentación
 * del evaluador a un procedimiento.<br>
 * @author ssanchez
 */
public class CargarFundamentacionEvaluadorAction extends ProcedimientoBaseTaskAction {

	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		Boolean tieneDatosFundamentacion = wfProcedimientoServicio.obtenerEstadoFundamentacionEvaluador(idTaskInstance); 
		if(!tieneDatosFundamentacion) {
			addError(messages, "app.controlAdquisicion.noFinalizarTareaNoFundamentacionEval");
		}
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		ProcedimientoBean procedimiento = new ProcedimientoBean();
		procedimiento.setId(taskHelper.getIdProcedimiento());
		procedimiento.setEstado(EstadoProcedimiento.CON_EVALUACION);		
		
		wfProcedimientoServicio.modificarEstado(idTaskInstance,EstadoProcedimiento.CON_EVALUACION);
		
		super.executeEjecutarYTerminarTarea(mapping, form, request, response, messages, idTaskInstance);
	}
}