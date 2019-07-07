package com.fontar.data.impl.domain.codes.seguimiento.evaluacion;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Estados de Evaluación Seguimiento
 */
public enum EstadoEvaluacionSeguimiento implements Enumerable {

	PEND_RESULT("app.codes.evaluacionSeguimiento.estado.pendienteResultado"), 
	CON_RESULTADO("app.codes.evaluacionSeguimiento.estado.conResultado"),
	CONFIRMADA("app.codes.evaluacionSeguimiento.estado.confirmada"),
	ANULADA("app.codes.evaluacionSeguimiento.estado.anulada");	

	private String descripcion;

	private EstadoEvaluacionSeguimiento(String key) {
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
