package com.fontar.web.action.seguimientos.evaluaciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.fontar.web.util.ActionUtil;

/**
 * Action para visualizar/editar los datos generales de una 
 *  <code>Evaluación de Seguimiento</code>.<br>
 * @author ssanchez
 */
public class DatosGeneralesEvaluacionSeguimientoAction extends EvaluacionSeguimientoBaseAction {

	public ActionForward visualizarDatosGenerales(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		ActionUtil.checkValidEncryptionContext(messages);
		
		if (messages.isEmpty()) {		
			Long idEvaluacion = getIdEvaluacion(request);
			
			EvaluacionGeneralDTO evaluacionGeneralDTO = evaluarProyectoServicio.obtenerEvaluacionGeneral(idEvaluacion);
			request.setAttribute("evaluacion", evaluacionGeneralDTO);
			
			EvaluacionSeguimientoCabeceraDTO cabeceraDTO = evaluarProyectoServicio.obtenerCabeceraEvaluacionSeguimiento(idEvaluacion);
			request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);
		}
		
		if (!messages.isEmpty()) {
			saveErrors(request,messages);
			return mapping.findForward("invalid");

		} else {
			return mapping.findForward("success");
		}	
	}
	
	/**
	 * Carga los datos generales de una <code>Evaluación de Seguimiento</code>
	 * para mostrarlos en un formulario.<br>
	 */
	public ActionForward editarDatosGenerales(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		
		ActionMessages messages = new ActionMessages();
		ActionUtil.checkValidEncryptionContext(messages);
		
		if (messages.isEmpty()) {
			Long idEvaluacion = getIdEvaluacion(request);
			
			EvaluacionGeneralDTO evaluacionGeneralDTO = evaluarProyectoServicio.obtenerEvaluacionGeneral(idEvaluacion);
			request.setAttribute("evaluacion", evaluacionGeneralDTO);
			BeanUtils.copyProperties(form, evaluacionGeneralDTO);
			
			EvaluacionSeguimientoCabeceraDTO cabeceraDTO = evaluarProyectoServicio.obtenerCabeceraEvaluacionSeguimiento(idEvaluacion);
			request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);
			
		}
		
		if (!messages.isEmpty()) {
			saveErrors(request,messages);
			return mapping.findForward("invalid");

		} else {
			return mapping.findForward("success");
			
		}	
	}	
}
