package com.fontar.data.impl.domain.codes.paquete;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Estados de Paquete
 */
public enum EstadoPaquete implements Enumerable {

	INICIADO("app.codes.paquete.estado.iniciado"), CONTROLADO("app.codes.paquete.estado.controlado"), EN_EVALUACION(
			"app.codes.paquete.estado.enEvaluacion"), CONFIRMADO("app.codes.paquete.estado.confirmado"), ANULADO(
			"app.codes.paquete.estado.anulado");

	private String descripcion;

	private EstadoPaquete(String key) {
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
		return getDescripcion();
	}
}
