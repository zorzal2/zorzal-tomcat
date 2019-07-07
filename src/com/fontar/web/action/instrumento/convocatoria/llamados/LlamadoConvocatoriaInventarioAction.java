package com.fontar.web.action.instrumento.convocatoria.llamados;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.llamadoConvocatoria.EstadoLlamadoConvocatoria;
import com.pragma.web.action.BaseInventarioAction;

/**
 * Action de Inventario de llamado convocatoria
 * @author gboaglio, ssanchez
 * @version 1.01, 14/12/06
 */
public class LlamadoConvocatoriaInventarioAction extends BaseInventarioAction {
	@Override
	protected void initInventario(HttpServletRequest request) {
		setComboOptions(request);
	}

	private void setComboOptions(HttpServletRequest request) {
		CollectionHandler handler = new CollectionHandler();
		ArrayList estados = (ArrayList) handler.getComboFiltro(EstadoLlamadoConvocatoria.class);

		request.setAttribute("estados", estados);
	}

}
