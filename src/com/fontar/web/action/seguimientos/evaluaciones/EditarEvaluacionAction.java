package com.fontar.web.action.seguimientos.evaluaciones;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;

/**
 * Action para editar los evaluadores de
 * una evaluación.<br>
 * @author ssanchez
 */
public class EditarEvaluacionAction extends EvaluacionSeguimientoBaseAction {

	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		ActionUtil.checkValidEncryptionContext(messages);
		
		if (messages.isEmpty()) {		
			Long idEvaluacion = getIdEvaluacion(request);
			
			EvaluacionSeguimientoCabeceraDTO cabeceraDTO = evaluarProyectoServicio.obtenerCabeceraEvaluacionSeguimiento(idEvaluacion);
			request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);

			EvaluacionGeneralDTO evaluacionGeneralDTO = evaluarProyectoServicio.obtenerEvaluacionGeneral(idEvaluacion);
			BeanUtils.copyProperties(form,evaluacionGeneralDTO);
			request.setAttribute("evaluacion", evaluacionGeneralDTO);
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
}
