package com.fontar.data.impl.domain.codes.evaluacion;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Estados de Evaluación
 */
public enum EstadoEvaluacion implements Enumerable {

	PEND_AUTORIZ("app.codes.evaluacion.estado.pendienteAutorizacion"), 
	PEND_RESULT("app.codes.evaluacion.estado.pendienteResultado"), 
	CON_RESULTADO("app.codes.evaluacion.estado.conResultado"),
	CONFIRMADA("app.codes.evaluacion.estado.confirmada"),
	ANULADA("app.codes.evaluacion.estado.anulada"),
	NO_AUTORIZADA("app.codes.evaluacion.estado.noAutorizada");

	private String descripcion;

	private EstadoEvaluacion(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}

	@Override
	public String toString() {
		return this.descripcion;
	}
}
