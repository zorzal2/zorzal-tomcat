package com.fontar.data.impl.domain.codes.llamadoConvocatoria;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Estados de LLamados Convocatoria
 */
public enum EstadoLlamadoConvocatoria implements Enumerable {

	ACTIVO("app.codes.llamado.estado.activo"), ANULADO("app.codes.llamado.estado.anulado");

	private String descripcion;

	private EstadoLlamadoConvocatoria(String key) {
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
