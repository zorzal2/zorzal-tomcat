package com.fontar.web.decorator.workflow;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.web.decorator.TableDecoratorSupport;

public class ProcessInstanceWrapper extends TableDecoratorSupport {

	public String getLinkDetalle() {
		ProcessInstance processInstance = (ProcessInstance) this.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();

		sb.append("<a href=\"WFDetalleInstanciaProceso.do");
		sb.append("?id=");
		sb.append(processInstance.getId());
		sb.append("\"");
		sb.append("onclick=\"window.open('WFDetalleInstanciaProceso.do");
		sb.append("?id=");
		sb.append(processInstance.getId());
		sb.append("','window','scrollbars=1,menubar=0,location=0,resizable=1');return false\")");
		sb.append("><img src='images/lupa.gif' border=0></a>");

		return sb.toString();
	}
}
