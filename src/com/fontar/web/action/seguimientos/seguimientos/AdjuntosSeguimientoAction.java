package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.action.adjuntos.AdjuntoAction;
import com.fontar.web.form.AdjuntoUploadForm;

public class AdjuntosSeguimientoAction extends AdjuntoAction {
	
	private AdministrarSeguimientoServicio administrarSeguimientoService; 	
	
	
	/**
	 * Carga la pantalla con el inventario de adjuntos de un seguimiento
	 */
	@Override
	public ActionForward cargarDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
						
		setCabeceraVisualizacionSeguimiento(request);
		
		AdjuntoUploadForm uploadForm = (AdjuntoUploadForm) form;
		Long idSeguimiento = SessionHelper.getIdSeguimiento(request);
		uploadForm.setId(idSeguimiento);
		
		SeguimientoBean seguimiento = administrarSeguimientoService.obtenerSeguimiento(idSeguimiento);
		request.setAttribute("anulado", seguimiento.getEstado().equals(EstadoSeguimiento.ANULADO));
		
		return super.cargarDownload(mapping,form,request,response);

	}
	
	/**
	 * Carga la pantalla para dar de alta un adjunto a un seguimiento
	 */
	@Override
	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		setCabeceraVisualizacionSeguimiento(request);
				
		return super.cargar(mapping,form,request,response);
	}

	
	/**
	 * Persiste un nuevo adjunto asociado al seguimiento que se está cargando
	 * @throws Exception 
	 */
	@Override
	public ActionForward uploadAdjunto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {
		
		Long idSeguimiento = SessionHelper.getIdSeguimiento(request);
		
		AdjuntoUploadForm uploadForm = (AdjuntoUploadForm) form;
		uploadForm.setId(idSeguimiento);
		
		return super.uploadAdjunto(mapping,form,request,response);
		
	}

	/**
	 * 	Guarda en el request los datos necesarios para armar la cabecera    
	 */
	public void setCabeceraVisualizacionSeguimiento(HttpServletRequest request) {
		Long idSeguimiento = SessionHelper.getIdSeguimiento(request);
				
		ProyectoCabeceraDTO dto = administrarSeguimientoService.obtenerDatosCabeceraSeguimientoVisualizacion(idSeguimiento);		
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, dto);		
	}
	
	public void setAdministrarSeguimientoService(AdministrarSeguimientoServicio administrarSeguimientoService) {
		this.administrarSeguimientoService = administrarSeguimientoService;
	}

	public String borrame() {
		return null;
	}
}
