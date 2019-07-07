package com.fontar.web.decorator.workflow;

import org.jbpm.graph.def.ProcessDefinition;

import com.fontar.web.decorator.TableDecoratorSupport;

public class ProcessDefinitionWrapper extends TableDecoratorSupport {

	public String getLinkNuevo() {
		ProcessDefinition processDefinition = (ProcessDefinition) this.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();

		sb.append("<a href=\"WFNuevaInstanciaProceso.do");
		sb.append("?id=");
		sb.append(processDefinition.getId());
		sb.append("\"><img src='images/edicion.gif' border=0></a>");

		return sb.toString();
	}

	public String getLinkInstancias() {
		ProcessDefinition processDefinition = (ProcessDefinition) this.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();

		sb.append("<a href=\"WFInstanciasProceso.do");
		sb.append("?id=");
		sb.append(processDefinition.getId());
		sb.append("\"><img src='images/etapas.gif' border=0></a>");

		return sb.toString();
	}
}
