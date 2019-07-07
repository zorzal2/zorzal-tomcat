package com.fontar.web.action.seguimientos.controlAdquisiciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.impl.domain.bean.ProcedimientoBean;

/**
 * Action para visualizar los datos de un <code>ProcedimientoBean</code>.<br> 
 * @author ssanchez
 */
public class VisualizarProcedimientoAction extends ProcedimientoBaseTaskAction  {

	/**
	 * Muestra los datos de un <code>ProcedimientoBean</code>.<br>
	 * El procedimiento mostrado corresponde al <i>id</i> 
	 * enviádo como parámetro al action.<br>
	 * @author ssanchez
	 */
	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idProcedimiento = obtenerIdProcedimiento(request);
		
		ProcedimientoBean procedimiento = administrarProcedimientoServicio.obtenerProcedimiento(idProcedimiento);
		request.setAttribute("procedimiento", procedimiento);
		
		return mapping.findForward("success");
	}

}
