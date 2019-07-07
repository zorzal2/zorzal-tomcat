package com.fontar.web.action.seguimientos.controlAdquisiciones;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento;
import com.pragma.web.action.BaseInventarioAction;

public class ProcedimientoInventarioAction extends BaseInventarioAction {

	private AdministrarProcedimientoServicio administrarProcedimientoServicio;
	
	public void setAdministrarProcedimientoServicio(AdministrarProcedimientoServicio administrarProcedimientoServicio) {
		this.administrarProcedimientoServicio = administrarProcedimientoServicio;
	}

	@Override
	/**
	 * Inicialización del inventario
	 */
	protected void initInventario(HttpServletRequest request) {
		setInitValues(request);
	}

	/**
	 * Cargo los datos necesitados por los combobox usados en los filtros
	 */
	private void setInitValues(HttpServletRequest request) {
		CollectionHandler handler = new CollectionHandler();

		ArrayList estados = (ArrayList) handler.getComboFiltro(EstadoProcedimiento.class);
		request.setAttribute("estados", estados);
		
		Collection tiposAdquisicion = handler.getAdquisiciones();
		request.setAttribute("tiposAdquisicion",tiposAdquisicion);
	}
}
