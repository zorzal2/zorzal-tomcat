package com.fontar.data.impl.domain.codes.presentacionConvocatoria;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Estados de Presentaciones a Convocatorias
 */
public enum EstadoPresentacion implements Enumerable {

	INICIADA("app.codes.presentacion.estado.iniciada"), FINALIZADA("app.codes.presentacion.estado.finalizada"), ANULADA(
			"app.codes.presentacion.estado.anulada");

	private String descripcion;

	private EstadoPresentacion(String key) {
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
