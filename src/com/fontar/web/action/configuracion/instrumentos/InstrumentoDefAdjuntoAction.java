package com.fontar.web.action.configuracion.instrumentos;

import static com.fontar.data.Constant.CabeceraAttribute.INSTRUMENTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.data.impl.domain.dto.InstrumentoVisualizacionDTO;
import com.fontar.web.action.adjuntos.AdjuntoAction;
import com.fontar.web.form.AdjuntoUploadForm;

public class InstrumentoDefAdjuntoAction extends AdjuntoAction {

	private AdministrarInstrumentosServicio administrarInstrumentosServicio;
	
	public void setAdministrarInstrumentosServicio(AdministrarInstrumentosServicio administrarInstrumentosServicio) {
		this.administrarInstrumentosServicio = administrarInstrumentosServicio;
	}
	
	@Override
	public void setHeader(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long id = getId((AdjuntoUploadForm)form,request);
		
		InstrumentoVisualizacionDTO visualizacionDTO = administrarInstrumentosServicio.obtenerDatosVisualizacion(id,false); 
		request.setAttribute(INSTRUMENTO, visualizacionDTO);
		}
}
