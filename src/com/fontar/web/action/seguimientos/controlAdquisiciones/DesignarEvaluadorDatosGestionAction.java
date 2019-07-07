package com.fontar.web.action.seguimientos.controlAdquisiciones;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.pragma.util.FormUtil;

/**
 * Action para visualizar/editar los datos de gestion  
 * de un <code>ProcedimientoBean</code>.<br>
 * @author ssanchez
 */
public class DesignarEvaluadorDatosGestionAction extends CircuitoAutorizacionDatosGestionAction {

	@Override
	public ActionForward guardarDatosGestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		
		Long idProcedimiento = obtenerIdProcedimiento(request);

		Long idPersonaEvaluador = FormUtil.getLongValue(form,"idPersonaEvaluador");
		Date fechaAsignacionEvaluador = FormUtil.getDateValue(form,"fechaAsignacionEvaluador");
		String descripcionAsignacionEvaluador = FormUtil.getStringValue(form,"descripcionAsignacionEvaluador");
		
		administrarProcedimientoServicio.guardarEvaluadorTecnico(idProcedimiento,idPersonaEvaluador,fechaAsignacionEvaluador,descripcionAsignacionEvaluador);

		return mapping.findForward("success");
	}
}
