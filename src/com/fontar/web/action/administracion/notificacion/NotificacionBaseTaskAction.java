package com.fontar.web.action.administracion.notificacion;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;
import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.notificacion.AdministrarNotificacionServicio;
import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.bus.api.workflow.WFNotificacionServicio;
import com.fontar.data.Constant;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.ProyectoVisualizacionAssembler;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Action Base para las acciones de workflow de <code>Notificacion</code>.<br> 
 * @author ssanchez
 */
public class NotificacionBaseTaskAction extends GenericJbpmTaskAction {

	protected AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio;
	protected AdministrarProyectoServicio administrarProyectoServicio;
	protected AdministrarNotificacionServicio administrarNotificacionServicio;
	protected WFNotificacionServicio wfNotificacionServicio;
	
	public void setWfNotificacionServicio(WFNotificacionServicio wfNotificacionServicio) {
		this.wfNotificacionServicio = wfNotificacionServicio;
	}
	public void setAdministrarIdeaProyectoServicio(AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio) {
		this.administrarIdeaProyectoServicio = administrarIdeaProyectoServicio;
	}
	public void setAdministrarProyectoServicio(AdministrarProyectoServicio administrarProyectoServicio) {
		this.administrarProyectoServicio = administrarProyectoServicio;
	}
	public void setAdministrarNotificacionServicio(AdministrarNotificacionServicio administrarNotificacionServicio) {
		this.administrarNotificacionServicio = administrarNotificacionServicio;
	}

	/**
	 * Carga el dto de <code>NotificacionDTO</code> y el dto para la cabecera
	 * de <code>Proyecto</code> o <code>IdeaProyecto</code>.<br>
	 */
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		NotificacionDTO notificacionDTO = wfNotificacionServicio.obtenerNotificacion(idTaskInstance);
		request.setAttribute("notificacion", notificacionDTO);
		
		if (notificacionDTO.getProyectoRaiz().getClase().equals(Constant.InstanciaProyectoRaiz.PROYECTO)) {
			ProyectoVisualizacionDTO cabeceraDTO = (ProyectoVisualizacionDTO) administrarProyectoServicio.getProyectoDTO(notificacionDTO.getIdProyecto(),new ProyectoVisualizacionAssembler());
			request.setAttribute(PROYECTO, cabeceraDTO);
		
		} else if (notificacionDTO.getProyectoRaiz().getClase().equals(Constant.InstanciaProyectoRaiz.IDEA_PROYECTO)) {
			IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(notificacionDTO.getIdProyecto(),new IdeaProyectoCabeceraAssembler());
			request.setAttribute(IDEA_PROYECTO, cabeceraDTO);
		}
	}
}