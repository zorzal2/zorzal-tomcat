package com.fontar.web.action.administracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Este método es para la actualización de la recomendación final de los proyectos ya cargados (se corrió una única vez en fontar). 
 * @author gboaglio, ssanchez
 * @version 1.01, 21/12/06
 */

public class AdministrarProyectoAction extends ProyectoBaseAction {
		

	public ActionForward updateRecomendacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		this.getAdministrarProyectoServicio().modificarRecomendaciones();
		return mapping.findForward("success");
	}
	
}
