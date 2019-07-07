package com.fontar.web.decorator.proyectos.paquetes;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.dto.EvaluarResultadoProyectoDTO;
import com.fontar.web.decorator.TableDecoratorSupport;

public class ProyectoEvaluarFilaDTOWrapper extends TableDecoratorSupport {

	
	public String getSelectorEvaluacion() throws UnsupportedEncodingException {
		StringBuffer link = new StringBuffer();
		
		EvaluarResultadoProyectoDTO proyectoDto = (EvaluarResultadoProyectoDTO) this.getCurrentRowObject();
		if(proyectoDto.getIdEvaluacion() != null)
			link.append("<input type='checkbox' name='elegibleArray' value='" + proyectoDto.getIdEvaluacion().toString() + "' "
				+ proyectoDto.getElegibleString() + ">");
		
		return link.toString();
	}

	public String getLinkVerPresupuesto() {
		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=''>");
		link.append("Ver presup");
		link.append("</a>");

		return link.toString();
	}
}