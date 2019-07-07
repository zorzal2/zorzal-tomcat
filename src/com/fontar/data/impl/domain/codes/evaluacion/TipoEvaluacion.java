package com.fontar.data.impl.domain.codes.evaluacion;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Evaluacion
 */
public enum TipoEvaluacion implements Enumerable {

	EVAL_GEN("app.codes.evaluacion.tipo.evaluacionGeneral"), 
	EVAL_PAQ("app.codes.evaluacion.tipo.evaluacionPaquete"), 
	PROX_ETAPA("app.codes.evaluacion.tipo.proximaEtapa"), 
	FINAL_CONTROL("app.codes.evaluacion.tipo.finalizarControl"), 
	AUTORIZACION_PAGO("app.codes.evaluacion.tipo.autorizarPago"), 
	EVAL_JUNTA("app.codes.evaluacion.tipo.evaluacionJunta");

	private String descripcion;

	private TipoEvaluacion(String key) {
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
