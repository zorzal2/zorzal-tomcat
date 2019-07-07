package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

public class ConfirmacionPaquete implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {

		EstadoProyecto estado;
		Recomendacion recomendacion;
		String decide = "";
		
		//FF : parche para ver si me llaman de un test
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();
			WFProyectoServicio servicio = (WFProyectoServicio) ContextUtil.getBean("wfProyectoService");

			// TODO: FF / consultar servicio o variable si viene de test de unidad
			Long idProyecto = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_PROYECTO);

			if (idProyecto == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_PROYECTO, processName);
			}

			estado = servicio.obtenerEstadoProyecto(idProyecto);
			recomendacion = servicio.obtenerRecomendacionProyecto(idProyecto);
		} else {
			estado = (EstadoProyecto) executionContext.getVariable(JbpmConstants.VariableNames.ESTADO);
			recomendacion = (Recomendacion) executionContext.getVariable(JbpmConstants.VariableNames.RECOMENDACION);
		}
		
		decide = "controlado";
		
		// Para Directorio
		if (estado.equals(EstadoProyecto.CONT_DIR_ADJ) || estado.equals(EstadoProyecto.CONT_DIR_EVAL)) {
			decide = "controlado";
			return decide;
		}

		// Para Evaluacion
		if (estado.equals(EstadoProyecto.EVALUACION)) {
			decide = "evaluacion";
			return decide;
		}

		// Para Secretaria
		if (estado.equals(EstadoProyecto.CONT_SEC)) {
			decide = "controlado";
			return decide;
		}

		// Para Comision
		if (estado.equals(EstadoProyecto.CONT_COM )) {
			decide = "controlado";
			return decide;
		}
		
		// Para Adjudicación
		if (estado.equals(EstadoProyecto.ADJUDICADO)) {

			if (recomendacion.equals(Recomendacion.APROBADO)
				|| recomendacion.equals(Recomendacion.APROBADO_ADJUDICADO)
				|| recomendacion.equals(Recomendacion.ADJUDICADO)) {
				decide = "adjudicado";
			} else {
				decide = "adjudicado_modificacion_monto";
			}
			
			return decide;
		}

		// Para Reconsiderar
		if (estado.equals(EstadoProyecto.POS_RECON)) {
			decide = "reconsideracion";
			return decide;
		}

		// Pendiente Alicuota
		if (estado.equals(EstadoProyecto.PEND_ALIC)) {
			decide = "pendiente_alicuota";
			return decide;
		}

		return decide;
	}
}
