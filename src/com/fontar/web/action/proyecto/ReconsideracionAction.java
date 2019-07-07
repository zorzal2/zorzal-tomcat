package com.fontar.web.action.proyecto;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.assembler.EvaluarAdmisibilidadProyectoAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.EvaluarAdmisibilidadProyectoDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

public class ReconsideracionAction extends ProyectoBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);

		EvaluarAdmisibilidadProyectoDTO proyectoDTO = (EvaluarAdmisibilidadProyectoDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new EvaluarAdmisibilidadProyectoAssembler());

		request.setAttribute(CabeceraAttribute.PROYECTO, proyectoDTO);

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();
		ProyectoRaizDAO proyectoRaizDao = (ProyectoRaizDAO) ContextUtil.getBean("proyectoRaizDao");
		ProyectoBean proyecto = (ProyectoBean) proyectoRaizDao.read(new Long(idProyecto));

		Collection evaluacionesList = administrarEvaluacionesServicio.getEvaluaciones(proyecto, Constant.AdministrarEvaluacionAttribute.RECONSIDERACION);
		request.setAttribute("evaluadores", evaluacionesList);

		setComboOptions(proyecto, request);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		Date fecha = DateTimeUtil.getDate(BeanUtils.getSimpleProperty(form, "fecha"));

		String observacion = BeanUtils.getSimpleProperty(form, "observacion");
		String paso = BeanUtils.getSimpleProperty(form, "idProximoPaso");
		wfProyectoServicio.reconsiderarProyecto(fecha, paso, observacion, idTaskInstance);
	}

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}

	@SuppressWarnings("unchecked")
	private void setComboOptions(ProyectoBean proyecto, HttpServletRequest request) throws SQLException {
		
		CollectionHandler handler = new CollectionHandler();
		Collection proximosPasos = handler.getProximosPasos(
				proyecto.getInstrumento().getPermiteComision(), 
				proyecto.getInstrumento().getPermiteSecretaria());
		request.setAttribute("proximosPasos", proximosPasos);

	}
}