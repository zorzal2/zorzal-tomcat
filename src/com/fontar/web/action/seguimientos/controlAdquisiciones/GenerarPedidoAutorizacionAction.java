package com.fontar.web.action.seguimientos.controlAdquisiciones;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.pragma.util.FormUtil;

/**
 * Action para cargar el resultado uffa
 * a un procedimiento.<br>
 * @author ssanchez
 */
public class GenerarPedidoAutorizacionAction extends ProcedimientoBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		cargarCabeceraProcedimiento(request,idTaskInstance);
		
		ProcedimientoBean procedimiento = wfProcedimientoServicio.obtenerProcedimiento(idTaskInstance);
		request.setAttribute("tipoAdquisicion",procedimiento.getTipoAdquisicion().getDescripcion());
		request.setAttribute("codigo",procedimiento.getCodigo());
		
		List<ProcedimientoItemBean> listaItems = wfProcedimientoServicio.obtenerItemsProcedimientoNoFinalizados(idTaskInstance);
		request.setAttribute("listaItems",listaItems);
	}
	
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		Date fechaRecepcion = FormUtil.getDateValue(form,"fechaRecepcion");
		String descripcion = FormUtil.getStringValue(form, "descripcion");
		
		wfProcedimientoServicio.generarPedidoAutorizacion(idTaskInstance,fechaRecepcion,descripcion);
	}
}