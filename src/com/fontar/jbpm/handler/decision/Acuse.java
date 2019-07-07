package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.notificacion.AdministrarNotificacionServicio;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

/**
 * Verifica se la <code>Notificacion</code> requiere o no acuse
 * de recibo. En caso de requerir sale del <code>Decision</code>
 * mediante la transición <i>SI</i>, sino ... <i>¡NO!</i>.<br>
 * Handler usado en el Process Definition de <code>Notificacion</code>.<br> 
 * @author ssanchez
 */
public class Acuse implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {

		String decide = "";
		Boolean requiereAcuse; 
		
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();
			AdministrarNotificacionServicio administrarNotificacionServicio = (AdministrarNotificacionServicio) ContextUtil.getBean("administrarNotificacionService");
	
			Long idNotificacion = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_NOTIFICACION);
	
			if (idNotificacion == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_NOTIFICACION, processName);
			}
	
			requiereAcuse = administrarNotificacionServicio.requiereAcuseNotificacion(idNotificacion);
		} else{
			requiereAcuse = (Boolean) executionContext.getVariable(JbpmConstants.VariableNames.REQUIERE_ACUSE);
		}

		if (requiereAcuse) {
			decide = "SI";
		}
		else {
			decide = "NO";
		}

		return decide;
	}

}
