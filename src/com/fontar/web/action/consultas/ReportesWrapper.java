package com.fontar.web.action.consultas;

import org.displaytag.decorator.TableDecorator;

import com.fontar.consultas.reportes.Reporte;
import com.fontar.web.decorator.link.impl.VisualizarLink;

public class ReportesWrapper extends TableDecorator {

	public String getVisualizar(){
		Reporte reporte = (Reporte) this.getCurrentRowObject();
		VisualizarLink link = new VisualizarLink("InicializarReporte.do","app.alt.visualizarReporte", reporte.getNombre());
		link.setSimplePermissionsRequired(reporte.getPermissionRequired());
		return link.displayValue();
	}
}
