package com.fontar.web.action.seguimientos.controlAdquisiciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento;

/**
 * Action para asignar un evaluador técnico a un
 * procedimiento.<br>
 * @author ssanchez
 */
public class DesignarEvaluadorTecnicoAction extends ProcedimientoBaseTaskAction {
	
	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		Boolean tieneDatosEvaluador = wfProcedimientoServicio.obtenerEstadoEvaluadorTecnico(idTaskInstance); 
		if(!tieneDatosEvaluador) {
			addError(messages, "app.controlAdquisicion.noFinalizarTareaNoEval");
		}
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		wfProcedimientoServicio.modificarEstado(idTaskInstance,EstadoProcedimiento.EN_EVALUACION);
		
		super.executeEjecutarYTerminarTarea(mapping, form, request, response, messages, idTaskInstance);
	}
}