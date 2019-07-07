package com.fontar.web.decorator.configuracion.comisiones;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.ComisionBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.fontar.web.decorator.link.impl.EditarLink;
import com.fontar.web.decorator.link.impl.VisualizarLink;

/**
 * Wrapper de Comisiones
 * @author ssanchez
 * @version 1.01, 21/12/06
 */
public class ComisionesWrapper extends TableDecoratorSupport {

	public String getLinkVisualizar() throws UnsupportedEncodingException {
		ComisionBean lObject = (ComisionBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("ComisionVisualizar.do","app.alt.visualizarComision",lObject.getId());
		return visualizarLink.displayValue();
	}

	public String getLinkEditar() throws UnsupportedEncodingException {
		ComisionBean lObject = (ComisionBean) this.getCurrentRowObject();
		EditarLink editarLink = new EditarLink("ComisionEditar.do","app.alt.editarComision",lObject.getId());
		editarLink.setSimplePermissionsRequired("COMISIONES-EDITAR");
		return editarLink.displayValue();
	}

	public String getLinkSeleccion() throws UnsupportedEncodingException {
		ComisionBean lObject = (ComisionBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('Comision','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(lObject.getDenominacion()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}
	
	public String getLinkSeleccionInventario() throws UnsupportedEncodingException {
		
		ComisionBean lObject = (ComisionBean) this.getCurrentRowObject();
		StringBuffer link = new StringBuffer();
		link.append("<a href='#' onclick=\"");
		link.append("handleSelection({id:");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append(",displayValue:'");
		link.append(String.valueOf(lObject.getDenominacion()));
		link.append("'});window.close();");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	} 
}
