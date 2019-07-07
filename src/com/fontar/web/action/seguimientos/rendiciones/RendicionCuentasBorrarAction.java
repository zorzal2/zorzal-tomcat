package com.fontar.web.action.seguimientos.rendiciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.pragma.web.action.TemplateCargarGuardarAction;

public class RendicionCuentasBorrarAction extends TemplateCargarGuardarAction {

	private AdministrarSeguimientoServicio administrarSeguimientoService;
	
	
	/**
	 * Por el momento el borrado es directo, no hay que cargar ninguna pantalla
	 */
	@Override
	protected boolean useToken() {
		return false;
	}
	
	/**
	 * Llamo al servicio de borrar bitacora con el id pasado como parametro
	 */
	@Override
	protected void executeGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idRendicion = null;

		if (validateParameter(request, "id")) {
			idRendicion = new Long(request.getParameter("id"));
		}

		administrarSeguimientoService.eliminarRendicion(idRendicion);

	}

	/**
	 * 
	 * @param administrarSeguimientoService
	 */
	public void setAdministrarSeguimientoService(AdministrarSeguimientoServicio administrarSeguimientoService) {
		this.administrarSeguimientoService = administrarSeguimientoService;
	}

	
}
