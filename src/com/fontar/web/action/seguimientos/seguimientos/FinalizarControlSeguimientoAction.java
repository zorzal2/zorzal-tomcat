package com.fontar.web.action.seguimientos.seguimientos;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.SeguimientoDAO;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionCompactoDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.jbpm.manager.SeguimientoTaskInstanceManager;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;

/**
 * Finaliza y da un resultado final al 
 * conjunto de evaluaciones de un seguimiento.
 * @author ssanchez
 */
public class FinalizarControlSeguimientoAction extends SeguimientoBaseTaskAction {

	private static final String EVALUACIONES_LIST = "evaluacionesList";

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		SeguimientoVisualizacionCabeceraDTO seguimientoDTO = wfSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(idTaskInstance);
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, seguimientoDTO);
		
		request.setAttribute("recomendaciones", new CollectionHandler().getRecomendacionesEvaluacionSeguimiento());
		

		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();
		SeguimientoDAO seguimientoDAO = (SeguimientoDAO)ContextUtil.getBean("seguimientoDao");
		SeguimientoBean seguimientoBean = (SeguimientoBean) seguimientoDAO.read(idSeguimiento);

		Collection evaluacionesList = wfSeguimientoServicio.getEvaluaciones(seguimientoBean, Constant.AdministrarEvaluacionAttribute.FINALIZAR_CONTROL);
		request.setAttribute(EVALUACIONES_LIST, evaluacionesList);

		ResumenDeRendicionCompactoDTO rendiciones = administrarSeguimientoServicio.obtenerTotalesRendicionesParaFinalizarControlDeSeguimiento(idSeguimiento);
		request.setAttribute("rendiciones", rendiciones);
		
	}
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ActionUtil.checkValidEncryptionContext(messages);
		
		if(messages.isEmpty()){
		
			List<String> evalAbiertas = wfSeguimientoServicio.getEvaluacionesAbiertas(idTaskInstance);
			Iterator i = evalAbiertas.iterator();
			while (i.hasNext()) {
				addError(messages,"app.proyecto.noFinCtrlEvalAbierta",i.next());
			}
			
			if (messages.isEmpty()) {
				if (!wfSeguimientoServicio.tieneEvaluacionesTecnicaYContableConfirmadas(idTaskInstance)) {
					addError(messages,"app.proyecto.noFinCtrlNoTecnicaYContable");
				}
			}
		}
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		EvaluacionSeguimientoDTO evaluacion = new EvaluacionSeguimientoDTO();

		String fechaEvaluacion = BeanUtils.getProperty(form, "fechaEvaluacion");
		String fundamentacion = BeanUtils.getProperty(form, "fundamentacion");
		ResultadoEvaluacion resultado = ResultadoEvaluacion.valueOf(BeanUtils.getProperty(form, "recomendacion")); 

		evaluacion.setFecha(fechaEvaluacion);
		evaluacion.setFundamentacion(fundamentacion);

		wfSeguimientoServicio.finalizarControlEvaluacion(evaluacion, resultado, idTaskInstance);
	}
}