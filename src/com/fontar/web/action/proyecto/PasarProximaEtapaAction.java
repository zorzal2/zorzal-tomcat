package com.fontar.web.action.proyecto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.ProyectoPasarProximaEtapaAssembler;
import com.fontar.data.impl.domain.dto.ProyectoPasarProximaEtapaDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.fontar.web.util.ActionUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

public class PasarProximaEtapaAction extends GenericJbpmTaskAction {

	WFProyectoServicio wfProyectoServicio;

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ProyectoPasarProximaEtapaDTO proximaEtapaDTO = (ProyectoPasarProximaEtapaDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new ProyectoPasarProximaEtapaAssembler());
		request.setAttribute(CabeceraAttribute.PROYECTO, proximaEtapaDTO);

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		BeanUtils.setProperty(form, "idProyecto", idProyecto);

	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		String fundamentacion = BeanUtils.getProperty(form, "fundamentacion");
		wfProyectoServicio.pasarAProximaEtapaSinEvaluacion(fundamentacion, idTaskInstance);
	}

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}

}
