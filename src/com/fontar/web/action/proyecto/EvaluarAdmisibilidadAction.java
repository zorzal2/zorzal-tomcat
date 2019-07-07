package com.fontar.web.action.proyecto;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.EvaluarAdmisibilidadProyectoAssembler;
import com.fontar.data.impl.domain.dto.EvaluarAdmisibilidadProyectoDTO;
import com.pragma.util.DateTimeUtil;

public class EvaluarAdmisibilidadAction extends ProyectoBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);

		EvaluarAdmisibilidadProyectoDTO proyectoDTO = (EvaluarAdmisibilidadProyectoDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new EvaluarAdmisibilidadProyectoAssembler());

		request.setAttribute(CabeceraAttribute.PROYECTO, proyectoDTO);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		Date fecha = DateTimeUtil.getDate(BeanUtils.getSimpleProperty(form, "fecha"));

		String fundamentacion = BeanUtils.getSimpleProperty(form, "fundamentacion");
		String disposicion = BeanUtils.getSimpleProperty(form, "disposicion");
		String resultado = BeanUtils.getSimpleProperty(form, "resultado");
		String observacion = BeanUtils.getSimpleProperty(form, "observacion");

		wfProyectoServicio.cargarAdmisionAlProyecto(fecha, fundamentacion, disposicion, resultado, observacion, idTaskInstance);

	}
}