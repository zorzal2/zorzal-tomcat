package com.fontar.web.decorator.configuracion.fuenteFinanciamiento;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * Wrapper Fuente Financiamiento
 * @author mdberra, ssanchez
 * @version 1.01, 20/12/01
 */
public class FuenteFinanciamientoWrapper extends TableDecoratorSupport {


	public String getLinkVisualizar() throws UnsupportedEncodingException {
		FuenteFinanciamientoBean lObject = (FuenteFinanciamientoBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("FuenteFinanciamientoVisualizar.do","app.alt.visualizarFuenteFinanciamiento",lObject.getId());
		return visualizarLink.displayValue();
	}

	public String getLinkEditar() throws UnsupportedEncodingException {
		FuenteFinanciamientoBean lObject = (FuenteFinanciamientoBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("FuenteFinanciamientoEditar.do","app.alt.editarFuenteFinanciamiento",lObject.getId());
		editarLink.setSimplePermissionsRequired("FUENTESFINANCIAMIENTO-EDITAR");
		return editarLink.displayValue();
	}

	public String getLinkSeleccion() throws UnsupportedEncodingException {
		FuenteFinanciamientoBean lObject = (FuenteFinanciamientoBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('FuenteFinanciamiento','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(lObject.getDenominacion()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}

}