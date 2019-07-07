package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.seguimientos.seguimientos.AnalisisGastosSeguimientoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.data.impl.domain.dto.analisisDeGastos.AnalisisDeGastosDTO;
import com.fontar.seguridad.cripto.AccesoDenegadoException;
import com.fontar.util.SessionHelper;
import com.fontar.web.action.seguimientos.evaluaciones.EvaluacionSeguimientoBaseAction;

/** 
 * @author llobeto 
 */
public class SeguimientoAnalisisGastosAction extends EvaluacionSeguimientoBaseAction {

	private AnalisisGastosSeguimientoServicio analisisGastosSeguimientoServicio;
	
	/**
	 * Prepara la pantalla de Análisis de Pertinencia de Gastos para un Seguimiento con Rendiciones 
	 * 
	 */
	public ActionForward analisisDeGastosEvaluacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {	
			Long idEvaluacion = getIdEvaluacion(request);
			//Cabecera
			EvaluacionSeguimientoCabeceraDTO cabeceraDTO = evaluarProyectoServicio.obtenerCabeceraEvaluacionSeguimiento(idEvaluacion);
			request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);
	
			AnalisisDeGastosDTO analisisGastosDto;
			if(SessionHelper.getActionAuthorize(request)) {
				//Estoy en carga de resultado de evaluacion
				analisisGastosDto = analisisGastosSeguimientoServicio.obtenerDatosDeAnalisisDeGastosParaCargarResultadoDeEvaluacion(idEvaluacion);		
			} else {
				//estoy en visualizacion de evaluacion
				analisisGastosDto = analisisGastosSeguimientoServicio.obtenerDatosDeAnalisisDeGastosParaVisualizarEvaluacion(idEvaluacion);		
			}
			request.setAttribute("analisisDeGastos", analisisGastosDto);		
	
			if (analisisGastosDto == null) {								
				request.setAttribute("messages","app.msj.noHayPresupuesto");							
			}
		} catch(AccesoDenegadoException e) {
			request.setAttribute("messages","app.msj.datoCripto");
		}
		return mapping.findForward("success");
	}
	public ActionForward analisisDeGastosEvaluarGestionDePago(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Long idSeguimiento = getIdSeguimiento(request);
			SeguimientoVisualizacionCabeceraDTO cabeceraDTO = analisisGastosSeguimientoServicio.obtenerCabeceraDeSeguimiento(idSeguimiento);
			request.setAttribute(CabeceraAttribute.SEGUIMIENTO, cabeceraDTO);
			AnalisisDeGastosDTO analisisGastosDto = analisisGastosSeguimientoServicio.obtenerDatosDeAnalisisDeGastosParaEvaluarGestionDePago(idSeguimiento);
			request.setAttribute("analisisDeGastos", analisisGastosDto);		
		} catch(AccesoDenegadoException e) {
			request.setAttribute("messages","app.msj.datoCripto");
		}
		return mapping.findForward("success");
	}
	public ActionForward analisisDeGastosVisualizarSeguimiento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Long idSeguimiento = getIdSeguimiento(request);
			SeguimientoVisualizacionCabeceraDTO cabeceraDTO = analisisGastosSeguimientoServicio.obtenerCabeceraDeSeguimiento(idSeguimiento);
			request.setAttribute(CabeceraAttribute.SEGUIMIENTO, cabeceraDTO);
			AnalisisDeGastosDTO analisisGastosDto = analisisGastosSeguimientoServicio.obtenerDatosDeAnalisisDeGastosParaVisualizarSeguimiento(idSeguimiento);		
			request.setAttribute("analisisDeGastos", analisisGastosDto);		
		} catch(AccesoDenegadoException e) {
			request.setAttribute("messages","app.msj.datoCripto");
		}
		return mapping.findForward("success");
	}

	public AnalisisGastosSeguimientoServicio getAnalisisGastosSeguimientoServicio() {
		return analisisGastosSeguimientoServicio;
	}
	public void setAnalisisGastosSeguimientoServicio(AnalisisGastosSeguimientoServicio analisisGastosSeguimientoServicio) {
		this.analisisGastosSeguimientoServicio = analisisGastosSeguimientoServicio;
	}
}


