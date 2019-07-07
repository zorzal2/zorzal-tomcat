package com.fontar.web.action.evaluacion;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.evaluacion.EvaluarProyectoServicio;
import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.data.Constant;
import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Action para editar los evaluadores de
 * una evaluación.<br>
 * @author ssanchez
 */
public class EditarEvaluacionProyectoAction extends BaseMappingDispatchAction {

	protected EvaluarProyectoServicio evaluarProyectoServicio; 
	protected AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio;
	protected AdministrarProyectoServicio administrarProyectoServicio;
	protected AdministrarEvaluacionesServicio administrarEvaluacionesServicio;
	
	public void setEvaluarProyectoServicio(EvaluarProyectoServicio evaluarProyectoServicio) {
		this.evaluarProyectoServicio = evaluarProyectoServicio;
	}
	public void setAdministrarIdeaProyectoServicio(AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio) {
		this.administrarIdeaProyectoServicio = administrarIdeaProyectoServicio;
	}
	public void setAdministrarProyectoServicio(AdministrarProyectoServicio administrarProyectoServicio) {
		this.administrarProyectoServicio = administrarProyectoServicio;
	}
	public void setAdministrarEvaluacionesServicio(AdministrarEvaluacionesServicio administrarEvaluacionesServicio) {
		this.administrarEvaluacionesServicio = administrarEvaluacionesServicio;
	}

	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		ActionUtil.checkValidEncryptionContext(messages);
		
		if (messages.isEmpty()) {		
			Long idEvaluacion = getIdEvaluacion(request);
			
//			EvaluacionGeneralBean evaluacion = evaluarProyectoServicio.obtenerEvaluacionGeneralBean(idEvaluacion);
//			
//			EvaluacionGeneralAssembler assembler = EvaluacionGeneralAssembler.getInstance();
//			EvaluacionGeneralDTO evaluacionGeneralDTO = assembler.buildDto(evaluacion);
			EvaluacionGeneralDTO evaluacionGeneralDTO = evaluarProyectoServicio.obtenerEvaluacionGeneral(idEvaluacion);
			BeanUtils.copyProperties(form,evaluacionGeneralDTO);
//			request.setAttribute("evaluacion", evaluacionGeneralDTO);
			
			EvaluacionDTO dto = administrarEvaluacionesServicio.getEvaluacionDTO(idEvaluacion, new VisualizarEvaluacionAssembler());
			request.setAttribute(Constant.CabeceraAttribute.EVALUACION, dto);

			
//			Long idProyecto = evaluacion.getIdProyecto();
//			if(evaluacion.getProyecto() instanceof ProyectoBean) {
//				
//				ProyectoVisualizacionDTO visualizacionProyectoDto = administrarProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
//				request.setAttribute(PROYECTO, visualizacionProyectoDto);
//				request.setAttribute("tipoCabecera",Constant.InstanciaProyectoRaiz.PROYECTO);
//				
//			} else if(evaluacion.getProyecto() instanceof IdeaProyectoBean) {
//
//				IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(idProyecto, new IdeaProyectoCabeceraAssembler());
//				request.setAttribute(IDEA_PROYECTO, cabeceraDTO);
//				request.setAttribute("tipoCabecera",Constant.InstanciaProyectoRaiz.IDEA_PROYECTO);
//			}
		}
		
		if (!messages.isEmpty()) {
			saveErrors(request,messages);
			return mapping.findForward("invalid");

		} else {
			return mapping.findForward("success");
		}	
	}
	
	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		EvaluacionGeneralDTO evaluacionDTO = populateEvaluacion(form);
		
		administrarEvaluacionesServicio.modificarEvaluacion(getIdEvaluacion(request), evaluacionDTO);
		
		return mapping.findForward("success");
	}
	
	private EvaluacionGeneralDTO populateEvaluacion(ActionForm form) throws Exception {

		EvaluacionGeneralDTO evaluacion = new EvaluacionGeneralDTO();

		evaluacion.setFechaInicial(BeanUtils.getProperty(form,"fechaInicial"));
		evaluacion.setFechaEntregaComprometida(BeanUtils.getProperty(form,"fechaEntregaComprometida"));

		evaluacion.setObservacion(BeanUtils.getProperty(form,"observacion"));

		String[] idEvaluador = FormUtil.getStringArrayValue(form,"idEvaluador");
		String[] idEntidadEvaluadora = FormUtil.getStringArrayValue(form,"idEntidadEvaluadora");
		String[] lugar = FormUtil.getStringArrayValue(form,"lugar");

		Collection<EvaluacionEvaluadorDTO> listaEvaluacionEvaluador = new ArrayList<EvaluacionEvaluadorDTO>();
		if (idEvaluador != null) {
			for (int i = 0; i < idEvaluador.length; i++) {

				EvaluacionEvaluadorDTO evaluadorDTO = new EvaluacionEvaluadorDTO();
				evaluadorDTO.setIdEntidadEvaluadora(idEntidadEvaluadora[i]);
				evaluadorDTO.setIdEvaluador(idEvaluador[i]);
				evaluadorDTO.setLugar(lugar[i]);
				listaEvaluacionEvaluador.add(evaluadorDTO);
			}

			evaluacion.setEvaluadores(listaEvaluacionEvaluador);
		}

		return evaluacion;
	}
	
	/**
	 * Obtiene el <i>idEvaluacion</i> desde el request
	 * y lo mete en la sesión.<br>
	 * Si el <i>idEvaluacion</i> no esta en el request
	 * lo busca en la sesión.<br>
	 * @param request
	 * @return idEvaluacion
	 */
	protected Long getIdEvaluacion(HttpServletRequest request){
		Long idEvaluacion = null;
		
		if (validateParameter(request,"id")){
			idEvaluacion = new Long(request.getParameter("id"));
			SessionHelper.setIdEvaluacion(request,idEvaluacion);
		} else {
			idEvaluacion = SessionHelper.getIdEvaluacion(request);
		}
		return idEvaluacion;
	}	
}
