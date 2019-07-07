package com.fontar.web.decorator.configuracion.instrumentos;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.InstrumentoDefBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.AdjuntosLink;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * Wrapper de InstrumentosDef
 * @author ssanchez
 * @version 1.01, 20/12/06
 */

public class InstrumentosDefWrapper extends TableDecoratorSupport{

	public String getLinkVisualizar() throws UnsupportedEncodingException {
		InstrumentoDefBean lObject = (InstrumentoDefBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("InstrumentosDefVisualizar.do","app.alt.visualizarInstrumentoDef",lObject.getId());
		visualizarLink.setSimplePermissionsRequired("INSTRUMENTOS-INVENTARIO");
		return visualizarLink.displayValue();
	}

	public String getLinkEditar() throws UnsupportedEncodingException {
		InstrumentoDefBean lObject = (InstrumentoDefBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("InstrumentosDefEditar.do","app.alt.editarInstrumentoDef",lObject.getId());
		editarLink.setSimplePermissionsRequired("INSTRUMENTOS-EDITAR");
		return editarLink.displayValue();
	}
	
	public String getLinkAdjuntos() throws UnsupportedEncodingException
	{
		InstrumentoDefBean lObject= (InstrumentoDefBean)this.getCurrentRowObject();
		AdjuntosLink adjuntosLink = new AdjuntosLink(lObject.getId(),InstrumentoDefBean.class);
		adjuntosLink.setSimplePermissionsRequired("INSTRUMENTOS-EDITAR");
		return adjuntosLink.displayValue();
	}
}
