package com.fontar.web.action.seguimientos.controlAdquisiciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant;

/**
 * Action para remitir a bid los items
 * de un procedimiento.<br>
 * @author ssanchez
 */
public class RemitirBidAction extends ProcedimientoBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		
		request.setAttribute("filtroItem", Constant.FiltroProcedimientoItem.ITEMS_BID);
	}

	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		Boolean tieneDatosBid = wfProcedimientoServicio.obtenerEstadoDatosRemisionBid(idTaskInstance); 
		if(!tieneDatosBid) {
			addError(messages, "app.controlAdquisicion.noFinalizarTareaNoRemisionBid");
		}
	}
}