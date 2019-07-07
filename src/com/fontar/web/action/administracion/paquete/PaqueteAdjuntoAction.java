package com.fontar.web.action.administracion.paquete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.paquete.AdministrarPaqueteServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.VisualizarPaqueteDTO;
import com.fontar.web.action.adjuntos.AdjuntoAction;
import com.fontar.web.form.AdjuntoUploadForm;

public class PaqueteAdjuntoAction extends AdjuntoAction {

	private AdministrarPaqueteServicio administracionPaqueteServicio;

	public void setAdministracionPaqueteServicio(AdministrarPaqueteServicio administracionPaqueteServicio) {
		this.administracionPaqueteServicio = administracionPaqueteServicio;
	}

	@Override
	public void setHeader(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		Long id = getId((AdjuntoUploadForm)form,request);
		VisualizarPaqueteDTO paqueteDto = administracionPaqueteServicio.obtenerVisualizacionPaquete(id);
		request.setAttribute(CabeceraAttribute.PAQUETE,paqueteDto);
	}

}
