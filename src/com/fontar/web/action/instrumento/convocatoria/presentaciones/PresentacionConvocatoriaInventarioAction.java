package com.fontar.web.action.instrumento.convocatoria.presentaciones;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.convocatoria.LlamadoConvocatoriaServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.presentacionConvocatoria.EstadoPresentacion;
import com.pragma.web.action.BaseInventarioAction;

/**
 * Action de inventario de Presentación de Convocatorias
 * @author gboaglio, ssanchez
 * @version 1.01, 14/12/06
 */
public class PresentacionConvocatoriaInventarioAction extends BaseInventarioAction {

	LlamadoConvocatoriaServicio llamadoConvocatoriaServicio;

	public void setLlamadoConvocatoriaServicio(
			LlamadoConvocatoriaServicio llamadoConvocatoriaServicio) {
		this.llamadoConvocatoriaServicio = llamadoConvocatoriaServicio;
	}

	@Override
	protected void initInventario(HttpServletRequest request) throws Exception {
		setComboOptions(request);
	}

	private void setComboOptions(HttpServletRequest request) throws Exception {
		CollectionHandler handler = new CollectionHandler();
		ArrayList estados = (ArrayList) handler.getComboFiltro(EstadoPresentacion.class);
		request.setAttribute("estados", estados);

		ArrayList convocatorias = (ArrayList) handler.getLabelValueLlamadosConvocatoria(llamadoConvocatoriaServicio.getLlamadosConvocatorias());
		request.setAttribute("convocatorias", convocatorias);
	}

}
