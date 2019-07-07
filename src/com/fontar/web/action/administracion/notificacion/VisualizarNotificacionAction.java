package com.fontar.web.action.administracion.notificacion;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;
import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.Constant;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.ProyectoVisualizacionAssembler;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;

/**
 * Action para visualizar los datos de una <code>Notificacion</code>.<br> 
 * @author ssanchez
 */
public class VisualizarNotificacionAction extends NotificacionBaseTaskAction  {

	/**
	 * Muestra los datos de una <code>Notificacion</code>.<br>
	 * La <code>Notificacion</code> mostrada corresponde al <i>id</i> 
	 * enviádo como parámetro al action.<br>
	 * @author ssanchez
	 */
	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		NotificacionDTO notificacionDTO = administrarNotificacionServicio.obtenerNotificacion(new Long(request.getParameter("id")));
		request.setAttribute("notificacion", notificacionDTO);
		
		if (notificacionDTO.getProyectoRaiz().getClase().equals(Constant.InstanciaProyectoRaiz.PROYECTO)) {
			ProyectoVisualizacionDTO cabeceraDTO = (ProyectoVisualizacionDTO) administrarProyectoServicio.getProyectoDTO(notificacionDTO.getIdProyecto(),new ProyectoVisualizacionAssembler());
			request.setAttribute(PROYECTO, cabeceraDTO);
		
		} else if (notificacionDTO.getProyectoRaiz().getClase().equals(Constant.InstanciaProyectoRaiz.IDEA_PROYECTO)) {
			IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(notificacionDTO.getIdProyecto(),new IdeaProyectoCabeceraAssembler());
			request.setAttribute(IDEA_PROYECTO, cabeceraDTO);
		}

		return mapping.findForward("success");
	}

}
