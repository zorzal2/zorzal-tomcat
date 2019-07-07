package com.fontar.web.action.proyecto;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;
import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizEvaluarDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.DateTimeUtil;

public class SolicitarReconsideracionAction extends ProyectoRaizBaseTaskAction {
	
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		super.executeCargarTarea(mapping,form,request,response,messages,idTaskInstance);
		
		ProyectoRaizEvaluarDTO proyecto = wfProyectoRaizServicio.obtenerClaseProyectoRaiz(idTaskInstance);
		if (proyecto.getClase().compareToIgnoreCase(CabeceraAttribute.PROYECTO) == 0) {
			ProyectoCabeceraDTO proyectoCabeceraDTO = (ProyectoCabeceraDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new ProyectoCabeceraAssembler());		
			request.setAttribute(PROYECTO, proyectoCabeceraDTO);
			
			request.setAttribute("clase",Constant.InstanciaProyectoRaiz.PROYECTO);
		} else if (proyecto.getClase().compareToIgnoreCase(CabeceraAttribute.IDEA_PROYECTO) == 0) {
			IdeaProyectoCabeceraDTO ideaProyectoCabeceraDTO = (IdeaProyectoCabeceraDTO) wfIdeaProyectoServicio.getIdeaProyectoDTO(idTaskInstance, new IdeaProyectoCabeceraAssembler());
			request.setAttribute(IDEA_PROYECTO, ideaProyectoCabeceraDTO);
			
			request.setAttribute("clase",Constant.InstanciaProyectoRaiz.IDEA_PROYECTO);
		}
		
		Collection evaluacionesList = wfProyectoRaizServicio.obtenerEvaluaciones(idTaskInstance,Constant.AdministrarEvaluacionAttribute.RECONSIDERACION); 
		request.setAttribute("evaluadores", evaluacionesList);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		Date fecha = DateTimeUtil.getDate(BeanUtils.getSimpleProperty(form, "fecha"));
		String observacion = BeanUtils.getSimpleProperty(form, "observacion");

		wfProyectoRaizServicio.solicitarReconsideracionDeProyectoRaiz(fecha, observacion, idTaskInstance);
	}
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}
}