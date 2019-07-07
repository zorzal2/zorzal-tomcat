package com.fontar.web.action.seguimientos.seguimientos;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.util.SessionHelper;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * 
 * @author gboaglio
 * 
 */
public class SeguimientoBaseAction extends BaseMappingDispatchAction {

	protected AdministrarSeguimientoServicio administrarSeguimientoServicio;

	
	/**
	 * 
	 */
	protected Long getIdProyecto(HttpServletRequest request){
		Long idProyecto= null;
		
		//veo si viene por parametro 
		if (validateParameter(request,"idProyecto")){
			idProyecto = new Long(request.getParameter("idProyecto"));
			SessionHelper.setIdProyecto(request,idProyecto);
		} else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		return idProyecto;
	}

	/**
	 * 
	 */
	protected Long getIdSeguimiento(HttpServletRequest request){
		Long idSeguimiento = null;
		
		//veo si viene por parametro 
		if (validateParameter(request,"id")){
			idSeguimiento = new Long(request.getParameter("id"));
			SessionHelper.setIdSeguimiento(request,idSeguimiento);
		} else {
			idSeguimiento = SessionHelper.getIdSeguimiento(request);
		}
		return idSeguimiento;
	}

	
	/** Getters y Setters **/ 
	

	public AdministrarSeguimientoServicio getAdministrarSeguimientoServicio() {
		return administrarSeguimientoServicio;
	}

	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}
	
}
