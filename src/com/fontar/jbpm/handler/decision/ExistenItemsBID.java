package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

/**
 * Si el procedimiento tiene items con resultado
 * <i>Aprobado Pendiente del BID</i>, el <i>Decision</i>
 * sale por <i>SI</i>, en caso contrario <i>NO</i>.<br>
 * Handler usado en el Process Definition de <code>ControlAdquisicion</code>.<br> 
 * @author ssanchez
 */
public class ExistenItemsBID implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {

		String decide = "";
		Boolean conItemsParaBID; 
		
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();

			AdministrarProcedimientoServicio administrarProcedimientoServicio = (AdministrarProcedimientoServicio) ContextUtil.getBean("administrarProcedimientoService");

			Long idProcedimiento = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_PROCEDIMIENTO);
	
			if (idProcedimiento == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_PROCEDIMIENTO, processName);
			}
	
			conItemsParaBID = administrarProcedimientoServicio.obtenerTieneItemsParaBid(idProcedimiento);
		} else{
			conItemsParaBID = (Boolean) executionContext.getVariable(JbpmConstants.VariableNames.CON_ITEMS_BID);
		}

		if (conItemsParaBID) {
			decide = "SI";
		}
		else {
			decide = "NO";
		}

		return decide;		
	}
}
