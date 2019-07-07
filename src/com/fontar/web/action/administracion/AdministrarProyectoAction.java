package com.fontar.web.action.administracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Este m�todo es para la actualizaci�n de la recomendaci�n final de los proyectos ya cargados (se corri� una �nica vez en fontar). 
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
