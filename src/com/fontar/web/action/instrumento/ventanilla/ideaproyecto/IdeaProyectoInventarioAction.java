package com.fontar.web.action.instrumento.ventanilla.ideaproyecto;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.web.action.IventarioConEstadoAnuladoAction;

/**
 * 
 * @author gboaglio
 * 
 */
public class IdeaProyectoInventarioAction extends IventarioConEstadoAnuladoAction {
	@Override
	protected void initInventario(HttpServletRequest request) {
		setComboOptions(request);
	}

	private void setComboOptions(HttpServletRequest request) {
		CollectionHandler handler = new CollectionHandler();
		ArrayList estados = (ArrayList) handler.getComboFiltroConEmptyLabelAll(EstadoIdeaProyecto.class);
		request.setAttribute("estados", estados);
	}
	
	protected String getPropertyName(){
		return "codigoEstado";
	}
}
