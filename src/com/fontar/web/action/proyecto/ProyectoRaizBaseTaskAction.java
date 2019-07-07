package com.fontar.web.action.proyecto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFIdeaProyectoServicio;
import com.fontar.bus.api.workflow.WFNotificacionServicio;
import com.fontar.bus.api.workflow.WFProyectoRaizServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Clase base para las tareas de proyecto
 * @author fferrara
 * 
 */
public class ProyectoRaizBaseTaskAction extends GenericJbpmTaskAction {

	protected WFProyectoRaizServicio wfProyectoRaizServicio;
	protected WFProyectoServicio wfProyectoServicio;
	protected WFIdeaProyectoServicio	wfIdeaProyectoServicio;
	protected WFNotificacionServicio wfNotificacionServicio;
	
	public WFIdeaProyectoServicio getWfIdeaProyectoServicio() {
		return wfIdeaProyectoServicio;
	}
	public void setWfIdeaProyectoServicio(WFIdeaProyectoServicio wfIdeaProyectoServicio) {
		this.wfIdeaProyectoServicio = wfIdeaProyectoServicio;
	}
	public WFProyectoRaizServicio getWfProyectoRaizServicio() {
		return wfProyectoRaizServicio;
	}
	public void setWfProyectoRaizServicio(WFProyectoRaizServicio wfProyectoRaizServicio) {
		this.wfProyectoRaizServicio = wfProyectoRaizServicio;
	}
	public WFProyectoServicio getWfProyectoServicio() {
		return wfProyectoServicio;
	}
	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}
	public WFNotificacionServicio getWfNotificacionServicio() {
		return wfNotificacionServicio;
	}
	public void setWfNotificacionServicio(WFNotificacionServicio wfNotificacionServicio) {
		this.wfNotificacionServicio = wfNotificacionServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ProyectoRaizDTO proyectoRaizDTO =  (ProyectoRaizDTO) wfProyectoRaizServicio.obtenerProyectoRaiz(idTaskInstance);
		BeanUtils.setProperty(form, "idProyecto", proyectoRaizDTO.getId());
	}
}
