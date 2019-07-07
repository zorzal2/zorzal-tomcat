package com.fontar.web.action.proyecto;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.DateTimeUtil;

public class AnalizarReconsideracionAction extends ProyectoBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		ProyectoVisualizacionDTO visualizacionDTO = (ProyectoVisualizacionDTO) wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(CabeceraAttribute.PROYECTO, visualizacionDTO);
		request.setAttribute("idProyecto", idProyecto);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		Date fecha = DateTimeUtil.getDate(BeanUtils.getSimpleProperty(form, "fecha"));	
		String fundamentacion = BeanUtils.getSimpleProperty(form, "fundamentacion");
		String resultado = BeanUtils.getSimpleProperty(form, "resultado");
		String resolucion = BeanUtils.getSimpleProperty(form, "resolucion");
		String observacion = BeanUtils.getSimpleProperty(form, "observacion");
		String dictamen = BeanUtils.getSimpleProperty(form, "dictamen");

		wfProyectoServicio.analizarReconsideracionAlProyecto(fecha, fundamentacion, resultado, resolucion, observacion, idTaskInstance, dictamen);

	}

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}

}