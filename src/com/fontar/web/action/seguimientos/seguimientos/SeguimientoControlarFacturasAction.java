package com.fontar.web.action.seguimientos.seguimientos;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.impl.domain.dto.ControlFacturasDTO;

/** 
 * @author gboaglio 
 */
 
public class SeguimientoControlarFacturasAction extends SeguimientoBaseAction {
	
	
	/**
	 * Prepara la pantalla de Control de Facturas para un seguimiento 
	 * 
	 */
	public ActionForward controlarFacturas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Long idSeguimiento = getIdSeguimiento(request);
		
		List<ControlFacturasDTO> facturasRepetidas = administrarSeguimientoServicio.obtenerFacturasRepetidas(idSeguimiento);


		request.setAttribute("facturasRepetidasList", facturasRepetidas);		
		
		return mapping.findForward("success");
	}


	
	
}


