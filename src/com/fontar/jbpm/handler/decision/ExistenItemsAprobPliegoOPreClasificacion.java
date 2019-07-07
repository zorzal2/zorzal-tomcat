package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

/**
 * Si el procedimiento tiene items con Resultados de la UFFA/BID iguales a 
 * <i>Aprobación del Pliego</i> o <i>Aprobación Pliego de Pre-Calificación</i>
 * el decision sale por <i>SI</i>, en caso contrario <i>NO</i>.<br>
 * Handler usado en el Process Definition de <code>ControlAdquisicion</code>.<br> 
 * @author ssanchez
 */
public class ExistenItemsAprobPliegoOPreClasificacion implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {

		String decide = "";
		Boolean conItemsPliegoOPreClasificacion; 
		
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();

			AdministrarProcedimientoServicio administrarProcedimientoServicio = (AdministrarProcedimientoServicio) ContextUtil.getBean("administrarProcedimientoService");

			Long idProcedimiento = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_PROCEDIMIENTO);
	
			if (idProcedimiento == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_PROCEDIMIENTO, processName);
			}
	
			conItemsPliegoOPreClasificacion = administrarProcedimientoServicio.obtenerTieneItemsAprobadosParcialmente(idProcedimiento);
		} else{
			conItemsPliegoOPreClasificacion = (Boolean) executionContext.getVariable(JbpmConstants.VariableNames.CON_ITEMS_PLIEGO_PRECLASIFICACION);
		}

		if (conItemsPliegoOPreClasificacion) {
			decide = "SI";
		}
		else {
			decide = "NO";
		}

		return decide;					
	}
}
