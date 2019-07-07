package com.fontar.web.action.instrumento.pitec;

import com.fontar.web.action.administracion.ProyectoBaseAction;

/** 
 * 
 * @author gboaglio
 * 
 */

public class AdministrarIdeaProyectoPitecAction extends ProyectoBaseAction {

	@Override
	public String getSubmitAction() {
		return "/IdeaProyectoPitecGuardar";
	}

}
