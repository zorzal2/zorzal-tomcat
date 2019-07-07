package com.fontar.data.impl.domain.codes.paquete;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tratamientos de Paquete
 */
public enum TratamientoPaquete implements Enumerable {

	EVALUACION("app.codes.paquete.tratamiento.evaluacion"), RECONSIDERACION(
			"app.codes.paquete.tratamiento.reconsideracion"), ADJUDICACION("app.codes.paquete.tratamiento.adjudicacion");

	private String descripcion;

	private TratamientoPaquete(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
