package com.fontar.web.action.administracion;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.fontar.web.action.proyecto.ProyectoBaseTaskAction;
import com.pragma.util.FormUtil;

public class AlicuotaAction extends ProyectoBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();
		ProyectoVisualizacionDTO visualizacionDTO = (ProyectoVisualizacionDTO) wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		
		request.setAttribute(CabeceraAttribute.PROYECTO, visualizacionDTO);
		BigDecimal alicuota = administrarProyectoServicio.obtenerAlicuotaSolicitada(idProyecto);
		BeanUtils.setProperty(form, "alicuota", alicuota);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		BigDecimal porcentaje = FormUtil.getBigDecimalValue(form, "alicuota");
		String observaciones = FormUtil.getStringValue(form, "observaciones");
		wfProyectoServicio.cargarAlicuota(porcentaje, observaciones, idTaskInstance);
	}
}