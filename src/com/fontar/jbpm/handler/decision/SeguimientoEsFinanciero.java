package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

/**
 * Determina la transición a seguir según sea un 
 * <code>Seguimiento</code> <i>financiero</i> o no.<br>
 * @author ssanchez
 */
public class SeguimientoEsFinanciero implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {

		Boolean esFinanciero;
		String decide = "";
		
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();
			AdministrarSeguimientoServicio administrarSeguimientoServicio = (AdministrarSeguimientoServicio) ContextUtil.getBean("administracionSeguimientoService");

			Long idSeguimiento = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_SEGUIMIENTO);
			if (idSeguimiento == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_SEGUIMIENTO, processName);
			}

			esFinanciero = administrarSeguimientoServicio.esFinanciero(idSeguimiento);
		} else {
			esFinanciero = Boolean.valueOf((String)executionContext.getVariable(JbpmConstants.VariableNames.ES_FINANCIERO)); 
		}
		

		if (esFinanciero) {
			decide = "SI";
			return decide;
		} else {
			decide = "NO";
			return decide;
		}
	}
}
