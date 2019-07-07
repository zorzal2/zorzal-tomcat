package com.fontar.web.action.proyecto;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;
import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFIdeaProyectoServicio;
import com.fontar.bus.api.workflow.WFProyectoRaizServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.InstanciaProyectoRaiz;
import com.fontar.data.impl.assembler.EvaluacionEvaluadorAssembler;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacionFinanciera;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizEvaluarDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Action para agregar una evaluación a un proyecto o idea proyecto
 * @author fferrara, ssanchez
 * @version 1.01, 09/01/07 
 */
public class EvaluarProyectoAction extends GenericJbpmTaskAction {

	protected WFProyectoRaizServicio wfProyectoRaizServicio;
	protected WFProyectoServicio wfProyectoServicio;
	protected WFIdeaProyectoServicio wfIdeaProyectoServicio;
	
	/**
	 * Carga de datos para la vista de la tarea, también se extraen los datos de
	 * contexto desde la tarea.
	 */
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		ProyectoRaizEvaluarDTO proyecto = wfProyectoRaizServicio.obtenerDatosEvaluacion(idTaskInstance);
		BeanUtils.setProperty(form, "idProyecto", proyecto.getId());

		request.setAttribute("txtProyecto", proyecto.getCodigo());
		request.setAttribute("txtEstado", proyecto.getEstado());
		request.setAttribute("clase", proyecto.getClase());
		
		// EntidadEvaluadora es requerido
		String[] idEntidadEvaluadora = FormUtil.getStringArrayValue(form, "idEntidadEvaluadora");
		String[] idEvaluador = FormUtil.getStringArrayValue(form, "idEvaluador");
		String[] lugar = FormUtil.getStringArrayValue(form, "lugar");

		if (idEntidadEvaluadora != null) {
			EvaluacionEvaluadorAssembler assembler = EvaluacionEvaluadorAssembler.getInstance();
			Collection evaluadores = new LinkedList();
			for (int i = 0; i < idEntidadEvaluadora.length; i++) {
				EvaluacionEvaluadorDTO dto = assembler.buildDto(idEntidadEvaluadora[i], idEvaluador[i], lugar[i]);
				evaluadores.add(assembler.buildDto(dto));
			}
			request.setAttribute("evaluadores", evaluadores);
		}

		setCollections(request);

		//cargo datos de cabecera para proyecto o idea proyecto
		
		if (proyecto.getClase().equals(InstanciaProyectoRaiz.PROYECTO)) {
			ProyectoCabeceraDTO proyectoCabeceraDTO = (ProyectoCabeceraDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new ProyectoCabeceraAssembler());		
			request.setAttribute(PROYECTO, proyectoCabeceraDTO);
		} else
			if (proyecto.getClase().equals(InstanciaProyectoRaiz.IDEA_PROYECTO)) {
				IdeaProyectoCabeceraDTO ideaProyectoCabeceraDTO = (IdeaProyectoCabeceraDTO) wfIdeaProyectoServicio.getIdeaProyectoDTO(idTaskInstance, new IdeaProyectoCabeceraAssembler());
				request.setAttribute(IDEA_PROYECTO, ideaProyectoCabeceraDTO);
		}
	}

	private EvaluacionGeneralDTO populateEvaluacion(ActionForm form) throws Exception {

		// Cargo los resultados del formulario
		EvaluacionGeneralDTO evaluacion = new EvaluacionGeneralDTO();

		evaluacion.setFechaInicial(BeanUtils.getProperty(form, "fechaInicial"));
		evaluacion.setFechaEntregaComprometida(BeanUtils.getProperty(form, "fechaEntrega"));

		evaluacion.setObservacion(BeanUtils.getProperty(form, "observaciones"));
		evaluacion.setTipo(TipoEvaluacion.EVAL_GEN);

		if (BeanUtils.getProperty(form, "esEconomica").equals("on")) {
			evaluacion.setEsEconomica(String.valueOf(true));
		}
		if (BeanUtils.getProperty(form, "esTecnica").equals("on")) {
			evaluacion.setEsTecnica(String.valueOf(true));
		}
		if (BeanUtils.getProperty(form, "esFinanciera").equals("on")) {
			evaluacion.setEsFinanciera(String.valueOf(true));
			TipoEvaluacionFinanciera tipoEvaluacionFinanciera = TipoEvaluacionFinanciera.valueOf(BeanUtils.getProperty(form, "tipoEvaluacionFinanciera"));
			evaluacion.setTipoEvaluacionFinanciera(tipoEvaluacionFinanciera);
		}

		String[] idEvaluador = FormUtil.getStringArrayValue(form, "idEvaluador");
		String[] idEntidadEvaluadora = FormUtil.getStringArrayValue(form, "idEntidadEvaluadora");
		String[] lugar = FormUtil.getStringArrayValue(form, "lugar");

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

	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		if (BeanUtils.getProperty(form, "esFinanciera").equals("on")) {
			if(BeanUtils.getProperty(form, "tipoEvaluacionFinanciera") == "")
				addError(messages, "app.evaluacion.financieraTipoEvaluacion");
		}
	}
	
	@Override
	@SuppressWarnings("unused")
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		Long idProyecto = new Long(BeanUtils.getProperty(form, "idProyecto"));
		EvaluacionGeneralDTO evaluacion = populateEvaluacion(form);

		// llamo al servicio, además de cargar la evaluación da de alta su
		// workflow
		wfProyectoRaizServicio.cargarEvaluacionAProyecto(evaluacion, idTaskInstance);
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();
		Collection evaluacionFinanciera = new ArrayList();

		evaluacionFinanciera.addAll(collectionHandler.getTipoEvaluacionFinanciera());
		request.setAttribute("tipoEvaluacionFinanciera", evaluacionFinanciera);
	}

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}


	public WFProyectoRaizServicio getWfProyectoRaizServicio() {
		return wfProyectoRaizServicio;
	}
	public void setWfProyectoRaizServicio(WFProyectoRaizServicio wfProyectoRaizServicio) {
		this.wfProyectoRaizServicio = wfProyectoRaizServicio;
	}
	public void setWfIdeaProyectoServicio(WFIdeaProyectoServicio wfIdeaProyectoServicio) {
		this.wfIdeaProyectoServicio = wfIdeaProyectoServicio;
	}
	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}
}