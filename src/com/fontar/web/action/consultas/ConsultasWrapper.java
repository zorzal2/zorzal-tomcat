package com.fontar.web.action.consultas;

import org.displaytag.decorator.TableDecorator;

import com.fontar.consultas.Informe;
import com.fontar.web.decorator.link.impl.VisualizarLink;

public class ConsultasWrapper extends TableDecorator {

	
	public String getVisualizar(){
		Informe consulta = (Informe) this.getCurrentRowObject();
		VisualizarLink link = new VisualizarLink("InicializarConsulta.do","app.alt.visualizarConsulta", consulta.getNombre());
		link.setSimplePermissionsRequired(consulta.getPermissionRequired());
		return link.displayValue();
	}
}
