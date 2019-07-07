package com.fontar.web.action.instrumento.ventanilla.ventanilla;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.ventanillaPermanente.EstadoVentanillaPermanente;
import com.pragma.web.action.BaseInventarioAction;

/**
 * Action de Inventario de ventanillas permanente
 * @author gboaglio, ssanchez
 * @version 1.01, 14/12/06
 */
public class VentanillaPermanenteInventarioAction extends BaseInventarioAction {
	@Override
	protected void initInventario(HttpServletRequest request) {
		setComboOptions(request);
	}

	private void setComboOptions(HttpServletRequest request) {
		CollectionHandler handler = new CollectionHandler();
		ArrayList estados = (ArrayList) handler.getComboFiltro(EstadoVentanillaPermanente.class);

		request.setAttribute("estados", estados);
	}

}
