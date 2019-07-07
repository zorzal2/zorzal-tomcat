package com.fontar.web.action.seguimientos.controlAdquisiciones;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.pragma.util.FormUtil;

/**
 * Action para cargar el resultado uffa
 * a un procedimiento.<br>
 * @author ssanchez
 */
public class CargarResultadoUffaAction extends ProcedimientoBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		
		List<ProcedimientoItemBean> listaItems = wfProcedimientoServicio.obtenerItemsDeProcedimientoPendientesDeResultadoUffa(idTaskInstance);
		
		request.setAttribute("listaItems",listaItems);
	}
	
	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		Boolean tieneCargadoResultados = wfProcedimientoServicio.obtenerEstadoItemsResultadoUffa(idTaskInstance); 
		if(!tieneCargadoResultados) {
			addError(messages, "app.controlAdquisicion.noFinalizarTareaNoResultadoUffa");
		}
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		Date fechaResultadoUffa = FormUtil.getDateValue(form,"fechaResultadoUffa");
		String observacionResultadoUffa = FormUtil.getStringValue(form,"observacionResultadoUffa");
		
		wfProcedimientoServicio.cargarResultadoUffa(idTaskInstance,fechaResultadoUffa,observacionResultadoUffa);
	}
}