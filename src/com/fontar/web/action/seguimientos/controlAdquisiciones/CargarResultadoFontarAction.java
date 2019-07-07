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
 * Action para cargar el resultado evaluación
 * a un procedimiento.<br>
 * @author ssanchez
 */
public class CargarResultadoFontarAction extends ProcedimientoBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		
		List<ProcedimientoItemBean> listaItems = wfProcedimientoServicio.obtenerItemsPorProcedimiento(idTaskInstance);
		
		request.setAttribute("listaItems",listaItems);
	}
	
	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		Boolean tieneCargadoResultados = wfProcedimientoServicio.obtenerEstadoItemsResultadoFontar(idTaskInstance); 
		if(!tieneCargadoResultados) {
			addError(messages, "app.controlAdquisicion.noFinalizarTareaNoResultadoFontar");
		}
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		Date fechaResultadoFontar = FormUtil.getDateValue(form,"fechaResultadoFontar");
		String observacionResultadoFontar = FormUtil.getStringValue(form,"observacionResultadoFontar");
		
		wfProcedimientoServicio.cargarResultadoFontar(idTaskInstance,fechaResultadoFontar,observacionResultadoFontar);
	}
}