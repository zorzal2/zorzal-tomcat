package com.fontar.web.decorator.workflow;

import java.io.UnsupportedEncodingException;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.domain.Workflowable;
import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.TableDecoratorSupport;

/**
 * Decorator base para Entidades con Workflow asociado
 * @author fferrara
 * 
 */
public class BaseEntityWorkFlowWrapper extends TableDecoratorSupport {

	/**
	 * TaskInstances decorator
	 * 
	 * @return Select HTML code with TaskInstances
	 * @throws UnsupportedEncodingException
	 */
	public String getComboTareasWorkflow() throws UnsupportedEncodingException {
		Workflowable currentObject = (Workflowable)this.getCurrentRowObject();
		Long idWorkFlow = currentObject.getIdWorkFlow();
		StringBuffer link = new StringBuffer();
		
		if (idWorkFlow == null) {
			link.append("<select id=Entity" + idWorkFlow + ">");
			link.append("<option value=''>" + CollectionHandler.NO_WORKFLOW + "</option>");
			link.append("</select>");
		}
		else { 
			link.append("<select id=Entity" + idWorkFlow);
			link.append(" onFocus=\"window.status='cargando tareas WF';obtenerTareasWF(this,'" + idWorkFlow + "');\">");
			link.append("<option value=''>" + CollectionHandler.EMPTY_LABEL + "</option>");
			link.append("</select>");
			link.append("&nbsp;<IMG height='10' alt='");
			link.append(ResourceManager.getAltResource("app.alt.cargarTarea"));
			link.append("' src='images/flecha.gif' style='cursor: hand;'");
			link.append(" onclick=\"redirectPage('Entity" + idWorkFlow + "');\" width='10' align='bottom'>");
		}			
		return link.toString();
	}

	/**
	 * Workflow ProcessInstance PopUp detail
	 * @return HTML image link
	 */
	public String getLinkDetalleWorkflow() {
		Workflowable currentObject = (Workflowable)this.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();

		Long idWorkFlow = currentObject.getIdWorkFlow();

		if (idWorkFlow != null) {
			sb.append("<a href=\"WFDetalleInstanciaProceso.do");
			sb.append("?id=");
			sb.append(idWorkFlow);
			sb.append("\"");
			sb.append("onclick=\"window.open('WFDetalleInstanciaProceso.do");
			sb.append("?id=");
			sb.append(idWorkFlow);
			sb.append("','window','scrollbars=1,menubar=0,location=0,resizable=1');return false\")");
			sb.append("><img src='images/lupa.gif' border=0 alt=");
			sb.append("'");
			sb.append(ResourceManager.getAltResource("app.alt.detalleWorkflow"));
			sb.append("'");
			sb.append("></a>");
		}
		return sb.toString();
	}

}
