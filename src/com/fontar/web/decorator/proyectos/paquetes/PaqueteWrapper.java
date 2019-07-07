package com.fontar.web.decorator.proyectos.paquetes;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.web.decorator.link.impl.AdjuntosLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;

/**
 * 
 * @author ssanchez
 * 
 */
public class PaqueteWrapper extends BaseEntityWorkFlowWrapper {

	/**
	 * Visualiza un Paquete
	 * @author ssanchez
	 */
	public String getLinkVisualizar() throws UnsupportedEncodingException {
		PaqueteBean paqueteBean = (PaqueteBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("VisualizarPaquete.do","app.alt.visualizarPaquete",paqueteBean.getId());
		visualizarLink.setPermissionsByInstrumentoRequired(paqueteBean.getIdInstrumento(), "PAQUETES-VISUALIZAR");
		return visualizarLink.displayValue();
	}

	public String getLinkAdjuntos() throws UnsupportedEncodingException
	{
		PaqueteBean lObject= (PaqueteBean)this.getCurrentRowObject();		
		AdjuntosLink adjuntosLink = new AdjuntosLink(lObject.getId(),PaqueteBean.class);
		return adjuntosLink.displayValue();		
	}	
}
