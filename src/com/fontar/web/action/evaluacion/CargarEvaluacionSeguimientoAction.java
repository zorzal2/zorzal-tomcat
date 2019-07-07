package com.fontar.web.action.evaluacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.EvaluacionEvaluadorAssembler;
import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;

public class CargarEvaluacionSeguimientoAction extends EvaluacionBaseTaskAction {
	
	private WFSeguimientoServicio wfSeguimientoServicio;

	/**
	 * Valida contexto de encriptación.<br>
	 * También que no tenga evaluaciones técnicas y contables
	 * abiertas.
	 * @author ssanchez
	 */
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		ActionUtil.checkValidEncryptionContext(messages);
		
		if(messages.isEmpty()) {
			
			SeguimientoBean seguimiento = wfSeguimientoServicio.obtenerSeguimiento(idTaskInstance);
			if(!seguimiento.getEsFinanciero() || !seguimiento.getEsTecnico()){
				addError(messages,"app.seguimiento.noEvalSegTecnicoYContable");

			} else {
				
				Boolean tieneTecnica = wfSeguimientoServicio.tieneEvaluacionTecnicaAbierta(idTaskInstance);
				Boolean tieneVisitaTecnica = wfSeguimientoServicio.tieneEvaluacionVisitaTecnicaAbierta(idTaskInstance);
				Boolean tieneContable = wfSeguimientoServicio.tieneEvaluacionContableAbierta(idTaskInstance);
				Boolean tieneAuditoriaContable = wfSeguimientoServicio.tieneEvaluacionAuditoriaContableAbierta(idTaskInstance);
				
				if(tieneTecnica && tieneVisitaTecnica && tieneContable && tieneAuditoriaContable) {
					addError(messages,"app.seguimiento.noAgregarEvalTecnicaContableAbierta");
					
					List<EvaluacionSeguimientoBean> evaluacionesAbiertas = wfSeguimientoServicio.evaluacionesAbiertas(idTaskInstance);
					for (EvaluacionSeguimientoBean evaluacion : evaluacionesAbiertas) {
						String labelTipoEvaluacion = evaluacion.labelTipoEvaluacion();
						String id = evaluacion.getId().toString();
						addError(messages,"app.seguimiento.noEvalTipoAbierta",new String[]{labelTipoEvaluacion,id});
					}
				}
				
				request.setAttribute("tieneTecnica",tieneTecnica);
				request.setAttribute("tieneVisitaTecnica",tieneVisitaTecnica);
				request.setAttribute("tieneContable",tieneContable);
				request.setAttribute("tieneAuditoriaContable",tieneAuditoriaContable);
			}
		}
	}

//	/**
//	 * Valida que no se agregue una Evaluación
//	 * técnica o contable si ya existe
//	 * una del mismo tipo. 
//	 * @author ssanchez
//	 */
//	@Override
//	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
//		
//		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
//		Long idSeguimiento = taskHelper.getIdSeguimiento();
//		
//		List<EvaluacionGeneralBean> evalTecnicasAbiertas = wfSeguimientoServicio.evaluacionesTecnicasAbiertas(idSeguimiento);
//		List<EvaluacionGeneralBean> evalContablesAbiertas = wfSeguimientoServicio.evaluacionesContablesAbiertas(idSeguimiento);
//		
//		Boolean esTecnica = BeanUtils.getProperty(form, "esVisita").equals("on") || BeanUtils.getProperty(form, "esTecnica").equals("on"); 
//		if (esTecnica && evalTecnicasAbiertas.size()>0) {
//			
//			Iterator itTecnicas = evalTecnicasAbiertas.iterator();
//			while (itTecnicas.hasNext()) {
//				addError(messages,"app.seguimiento.noAgregarEvalTecnicaAbierta",((EvaluacionGeneralBean)itTecnicas.next()).getId());
//			}
//		}
//		
//		Boolean esContable = BeanUtils.getProperty(form, "esContable").equals("on") || BeanUtils.getProperty(form, "esAuditoria").equals("on");
//		if (esContable && evalContablesAbiertas.size()>0) {
//
//			Iterator itContables = evalContablesAbiertas.iterator();
//			while (itContables.hasNext()) {
//				addError(messages,"app.seguimiento.noAgregarEvalContableAbierta",((EvaluacionGeneralBean)itContables.next()).getId());
//			}
//		}
//	}
	
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		EvaluacionSeguimientoDTO evaluacion = populateEvaluacion(form);
		
		wfSeguimientoServicio.cargarEvaluacionASeguimiento(evaluacion, idTaskInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		
		SeguimientoVisualizacionCabeceraDTO seguimientoDTO = wfSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(idTaskInstance);
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, seguimientoDTO);

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
	}

	protected EvaluacionDTO decorate(EvaluacionDTO dto ){
		dto.setResumido();
		return dto;
	}

	public WFSeguimientoServicio getWfSeguimientoServicio() {
		return wfSeguimientoServicio;
	}

	public void setWfSeguimientoServicio(WFSeguimientoServicio wfSeguimientoServicio) {
		this.wfSeguimientoServicio = wfSeguimientoServicio;
	}

	private EvaluacionSeguimientoDTO populateEvaluacion(ActionForm form) throws Exception {

		// Cargo los resultados del formulario
		EvaluacionSeguimientoDTO evaluacion = new EvaluacionSeguimientoDTO();

		evaluacion.setFechaInicial(BeanUtils.getProperty(form, "fechaInicial"));
		evaluacion.setFechaEntregaComprometida(BeanUtils.getProperty(form, "fechaEntrega"));

		evaluacion.setObservacion(BeanUtils.getProperty(form, "observaciones"));
		evaluacion.setTipo(TipoEvaluacion.EVAL_GEN);

		if (BeanUtils.getProperty(form, "esVisita").equals("on")) {
			evaluacion.setEsVisitaTecnica(String.valueOf(true));
		}
		if (BeanUtils.getProperty(form, "esTecnica").equals("on")) {
			evaluacion.setEsTecnica(String.valueOf(true));
		}
		if (BeanUtils.getProperty(form, "esContable").equals("on")) {
			evaluacion.setEsContable(String.valueOf(true));
		}
		if (BeanUtils.getProperty(form, "esAuditoria").equals("on")) {
			evaluacion.setEsAuditoriaContable(String.valueOf(true));
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
}