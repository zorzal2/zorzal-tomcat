package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.bus.BusinessException;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Cierra un <code>Seguimiento</code>
 * finalizando el Workflow.<br>
 * @author ssanchez
 */
public class CerrarSeguimientoAction extends GenericJbpmTaskAction {

	private WFSeguimientoServicio wfSeguimientoServicio;

	public void setWfSeguimientoServicio(WFSeguimientoServicio wfSeguimientoServicio) {
		this.wfSeguimientoServicio = wfSeguimientoServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		if(!ActionUtil.isEncryptionContextAvailable()) {
			BusinessException Ex = new BusinessException();
			Ex.setBundleKey("app.error.encrypt");
			throw Ex;	
		}

		
		
		SeguimientoVisualizacionCabeceraDTO dto = wfSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(idTaskInstance);
	
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, dto);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
			String observaciones = FormUtil.getStringValue(form, "observacion");

			wfSeguimientoServicio.cerrarSeguimiento(observaciones, idTaskInstance);
	}
}