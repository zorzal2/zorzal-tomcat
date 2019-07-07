package com.fontar.web.action.bitacora;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.bitacora.AdministrarBitacoraServicio;
import com.pragma.web.action.TemplateCargarGuardarAction;

public class BitacoraBorrarAction extends TemplateCargarGuardarAction {

	private AdministrarBitacoraServicio administrarBitacoraServicio;

	public void setAdministrarBitacoraServicio(AdministrarBitacoraServicio administrarBitacoraServicio) {
		this.administrarBitacoraServicio = administrarBitacoraServicio;
	}

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

		Long idBitacora = null;

		if (validateParameter(request, "id")) {
			idBitacora = new Long(request.getParameter("id"));
		}

		administrarBitacoraServicio.eliminarBitacoraManual(idBitacora);
	}
}
